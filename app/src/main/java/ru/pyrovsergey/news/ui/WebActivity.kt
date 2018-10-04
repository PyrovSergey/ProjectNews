package ru.pyrovsergey.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_web.*
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.ArticlesItem

class WebActivity : AppCompatActivity() {

    private lateinit var article: ArticlesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        setSupportActionBar(findViewById(R.id.web_toolbar))
        init()
    }

    private fun init() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        article = intent.getSerializableExtra(KEY_ARTICLE) as ArticlesItem
        val newsWebView = findViewById<WebView>(R.id.news_web_view)
        newsWebView.webViewClient = WebViewClient()
        webToolbarTitle.text = article.source?.name
        newsWebView.loadUrl(article.url)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.web_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_web_menu_share -> PopupClass.share(article)
            R.id.action_web_menu_copy_url -> PopupClass.copy(article)
            R.id.action_web_menu_open_in_browser -> PopupClass.openInBrowser(article)
            R.id.action_web_menu_add_or_remove_to_bookmarks -> PopupClass.bookmarks(article)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
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
}
