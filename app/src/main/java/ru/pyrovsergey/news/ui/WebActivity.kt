package ru.pyrovsergey.news.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.github.zawadz88.materialpopupmenu.popupMenu
import kotlinx.android.synthetic.main.activity_web.*
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.ArticlesItem

class WebActivity : AppCompatActivity() {

    private lateinit var article: ArticlesItem
    private val pop = PopupClass

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
        customMenuButton.setOnClickListener { view ->
            onSingleSectionWithIconsClicked(view, article)
        }
    }

    private fun onSingleSectionWithIconsClicked(view: View, articlesItem: ArticlesItem) {
        val popupMenu = popupMenu {
            section {
                item {
                    label = getString(R.string.share)
                    icon = R.drawable.ic_share_black_24dp
                    callback = {
                        pop.share(articlesItem)
                    }
                }
                item {
                    label = getString(R.string.copy)
                    iconDrawable = ContextCompat.getDrawable(this@WebActivity, R.drawable.ic_content_copy_black_24dp) //optional
                    callback = {
                        pop.copy(articlesItem)
                    }
                }
                item {
                    label = getString(R.string.open_in_browser)
                    iconDrawable = ContextCompat.getDrawable(this@WebActivity, R.drawable.ic_browser_24dp) //optional
                    callback = {
                        pop.openInBrowser(articlesItem)
                    }
                }
                item {
                    label = if (!pop.inBookmark(articlesItem)) getString(R.string.add_to_bookmarks) else getString(R.string.remove_from_bookmarks)
                    iconDrawable = ContextCompat.getDrawable(this@WebActivity, R.drawable.ic_collections_bookmark_black_24dp) //optional
                    callback = {
                        pop.bookmarks(articlesItem)
                    }
                }
            }
        }
        popupMenu.show(this@WebActivity, view)
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
