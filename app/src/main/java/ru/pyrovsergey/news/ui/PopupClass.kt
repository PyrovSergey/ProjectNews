package ru.pyrovsergey.news.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import es.dmoral.toasty.Toasty
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.ArticlesItem

object PopupClass {
    private const val SHARE_TYPE = "text/html"

    fun share(articlesItem: ArticlesItem) {
        if (App.instance.checkInternetConnection()) {
            val intentShare = Intent(Intent.ACTION_SEND)
            intentShare.type = SHARE_TYPE
            intentShare.putExtra(Intent.EXTRA_TEXT, articlesItem.url)
            val chooser = Intent.createChooser(intentShare, "Look ")
            if (intentShare.resolveActivity(App.context.packageManager) != null) {
                App.context.startActivity(chooser)
            }
        }
    }

    fun copy(articlesItem: ArticlesItem) {
        val clipManager = App.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(SHARE_TYPE, articlesItem.url)
        clipManager.primaryClip = clipData
        showSuccessMessage("Link copied")
    }

    fun openInBrowser(articlesItem: ArticlesItem) {
        if (App.instance.checkInternetConnection()) {
            App.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(articlesItem.url)))
        }
    }

    fun bookmarks(articlesItem: ArticlesItem) {

    }

    private fun showSuccessMessage(message: String) {
        Toasty.success(App.context, message, 0, true).show()
    }

}
