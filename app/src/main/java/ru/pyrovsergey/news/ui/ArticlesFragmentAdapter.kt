package ru.pyrovsergey.news.ui


import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.squareup.picasso.Picasso
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.ArticlesItem
import java.text.SimpleDateFormat
import java.util.*

class ArticlesFragmentAdapter(private val listArticles: List<ArticlesItem>) : RecyclerView.Adapter<ArticlesFragmentAdapter.ViewHolder>() {

    private var pop = PopupClass
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listArticles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = listArticles[position]
        val urlImage = article.urlToImage
        val baseUrl = Uri.parse(article.url).host
        when(TextUtils.isEmpty(urlImage)){
            true -> Picasso.get().load("https://besticon-demo.herokuapp.com/icon?url=$baseUrl&size=64..100..120").placeholder(R.drawable.placeholder).into(holder.imageViewArticle)
            false -> Picasso.get().load(urlImage).placeholder(R.drawable.placeholder).into(holder.imageViewArticle)
        }
        holder.cardView.setOnClickListener { v ->
            if (App.instance.checkInternetConnection()) {
                WebActivity.startWebActivity(article)
            }
        }
        holder.textViewTitleArticle.text = article.title
        holder.textViewSourceNameArticle.text = article.source?.name ?: ""
        holder.textViewDatePublishedAtArticle.text = getDate(article.publishedAt)
        holder.imageViewButtonMoreArticle.setOnClickListener { v -> onSingleSectionWithIconsClicked(v, article) }
    }

    private fun onSingleSectionWithIconsClicked(view: View, articlesItem: ArticlesItem) {
        val popupMenu = popupMenu {
            section {
                item {
                    label = App.context.getString(R.string.share)
                    icon = R.drawable.ic_share_black_24dp
                    callback = {
                        pop.share(articlesItem)
                    }
                }
                item {
                    label = App.context.getString(R.string.copy)
                    iconDrawable = ContextCompat.getDrawable(App.context, R.drawable.ic_content_copy_black_24dp) //optional
                    callback = {
                        pop.copy(articlesItem)
                    }
                }
                item {
                    label = App.context.getString(R.string.open_in_browser)
                    iconDrawable = ContextCompat.getDrawable(App.context, R.drawable.ic_browser_24dp) //optional
                    callback = {
                        pop.openInBrowser(articlesItem)
                    }
                }
                item {
                    label = if (!pop.inBookmark(articlesItem)) App.context.getString(R.string.add_to_bookmarks) else App.context.getString(R.string.remove_from_bookmarks)
                    iconDrawable = ContextCompat.getDrawable(App.context, R.drawable.ic_collections_bookmark_black_24dp) //optional
                    callback = {
                        pop.bookmarks(articlesItem)
                    }
                }
            }
        }
        popupMenu.show(App.context, view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.article_card)
        val imageViewArticle: ImageView = view.findViewById(R.id.article_card_image)
        val textViewTitleArticle: TextView = view.findViewById(R.id.article_card_title)
        val textViewSourceNameArticle: TextView = view.findViewById(R.id.article_card_source_name)
        val imageViewButtonMoreArticle: ImageView = view.findViewById(R.id.article_card_button_more)
        val textViewDatePublishedAtArticle: TextView = view.findViewById(R.id.article_card_date_publishedAt)
    }

    private fun getDate(data: Date?): String {
        val dateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
        return dateFormat.format(data)
    }

    companion object {
        const val DATE_PATTERN = "HH:mm  dd MMMM yyyy"
    }
}