package ru.pyrovsergey.news

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_web.*
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.ArticlesItem
import ru.pyrovsergey.news.presenter.ViewWeb
import ru.pyrovsergey.news.presenter.WebPresenter

class WebActivity : MvpAppCompatActivity(), ViewWeb {

    @InjectPresenter
    lateinit var presenter: WebPresenter

    private lateinit var article: ArticlesItem
    private var inBookmarks = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        setSupportActionBar(findViewById(R.id.web_toolbar))
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        article = intent.getSerializableExtra(KEY_ARTICLE) as ArticlesItem
        val newsWebView = findViewById<WebView>(R.id.news_web_view)
        newsWebView.webViewClient = WebViewClient()
        webToolbarTitle.text = article.source?.name
        newsWebView.loadUrl(article.url)
        presenter.checkArticle(article.url)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_web_menu_share -> share()
            R.id.action_web_menu_copy_url -> copyUrl()
            R.id.action_web_menu_open_in_browser -> openInBrowser()
            R.id.action_web_menu_add_or_remove_to_bookmarks -> addOrRemoveToBookmarks()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun updateBindObject() {
        presenter.checkArticle(article.url)
    }

    private fun addOrRemoveToBookmarks() {
        if (inBookmarks) {
            presenter.removeArticleInBookmarks(article)
        } else {
            presenter.addArticleToBookmarks(article)
        }
    }

    override fun positiveCheckBookmarks(article: ArticlesItem) {
        inBookmarks = true
        this.article = article
    }

    override fun negativeCheckBookmarks() {
        inBookmarks = false
    }

    private fun share() {
        if (App.instance.checkInternetConnection()) {
            val intentShare = Intent(Intent.ACTION_SEND)
            intentShare.type = SHARE_TYPE
            intentShare.putExtra(Intent.EXTRA_TEXT, article.url)
            val chooser = Intent.createChooser(intentShare, "Look ")
            if (intentShare.resolveActivity(packageManager) != null) {
                startActivity(chooser)
            }
        }
    }

    private fun copyUrl() {
        val clipManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(SHARE_TYPE, article.url)
        clipManager.primaryClip = clipData
        showSuccessMessage("Link copied")
    }

    private fun openInBrowser() {
        if (App.instance.checkInternetConnection()) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
        }
    }

    companion object {
        private const val SHARE_TYPE = "text/html"
        private const val KEY_ARTICLE = "ru.pyrovsergey.news_key_article"
        fun startWebActivity(article: ArticlesItem) {
            val context = App.context
            val intent = Intent(context, WebActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val bundle = Bundle()
            bundle.putSerializable(KEY_ARTICLE, article)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }

    override fun showSuccessMessage(message: String) {
        Toasty.success(this, message, 0, true).show()
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(this, message, 0, true).show()
    }
}
