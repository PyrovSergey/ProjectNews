package ru.pyrovsergey.news.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import es.dmoral.toasty.Toasty
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.db.RepositoryListener
import ru.pyrovsergey.news.model.dto.ArticlesItem

object PopupClass : RepositoryListener {

    private val repository = App.getInstance().getComponent().getRepository()
    private const val SHARE_TYPE = "text/html"
    private var tmpArticlesItem: ArticlesItem? = null

    fun share(articlesItem: ArticlesItem) {
        if (App.getInstance().checkInternetConnection()) {
            val intentShare = Intent(Intent.ACTION_SEND)
            intentShare.type = SHARE_TYPE
            intentShare.putExtra(Intent.EXTRA_TEXT, articlesItem.url)
            val share = Intent.createChooser(intentShare, "Look ")
            share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (intentShare.resolveActivity(App.getInstance().getContext().packageManager) != null) {
                App.getInstance().getContext().startActivity(share)
            }
        }
    }

    fun copy(articlesItem: ArticlesItem) {
        val clipManager = App.getInstance().getContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(SHARE_TYPE, articlesItem.url)
        clipManager.primaryClip = clipData
        showSuccessMessage(App.getInstance().getContext().getString(R.string.link_copied))
    }

    fun openInBrowser(articlesItem: ArticlesItem) {
        if (App.getInstance().checkInternetConnection()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articlesItem.url))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            App.getInstance().getContext().startActivity(intent)
        }
    }

    private fun showSuccessMessage(message: String) {
        Toasty.success(App.getInstance().getContext(), message, 0, true).show()
    }

    private fun showErrorMessage(message: String) {
        Toasty.error(App.getInstance().getContext(), message, 0, true).show()
    }

    fun bookmarks(articlesItem: ArticlesItem) {
        tmpArticlesItem = articlesItem
        repository.checkBookmarks(articlesItem.url, this)
    }

    fun inBookmark(articlesItem: ArticlesItem): Boolean {
        return repository.containArticle(articlesItem)
    }

    override fun onSuccessDeleteBookmark() {
        showSuccessMessage(App.getInstance().getContext().getString(R.string.bookmark_deleted))
    }

    override fun onErrorDeleteBookmark() {
        showErrorMessage(App.getInstance().getContext().getString(R.string.error_delete_bookmark))
    }

    override fun onSuccessInsertBookmark() {
        showSuccessMessage(App.getInstance().getContext().getString(R.string.bookmark_added))
    }

    override fun onErrorInsertBookmark() {
        showErrorMessage(App.getInstance().getContext().getString(R.string.error_insert_bookmark))
    }

    override fun onSuccessRequestBookmarksList() {
    }

    override fun positiveCheckResultBookmarks(articlesItem: ArticlesItem) {
        repository.deleteBookmark(articlesItem, this)
    }

    override fun negativeCheckResultBookmarks() {
        repository.insertBookmark(tmpArticlesItem!!, this)
    }
}
