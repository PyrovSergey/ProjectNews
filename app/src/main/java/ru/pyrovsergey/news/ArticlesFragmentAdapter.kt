package ru.pyrovsergey.news


import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.model.dto.Model
import java.text.SimpleDateFormat
import java.util.*

class ArticlesFragmentAdapter(private val listArticles: List<Model.ArticlesItem>) : RecyclerView.Adapter<ArticlesFragmentAdapter.ViewHolder>() {
    private val DATE_PATTERN = "HH:mm  dd MMMM yyyy"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesFragmentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listArticles.size
    }

    override fun onBindViewHolder(holder: ArticlesFragmentAdapter.ViewHolder, position: Int) {
        val article = listArticles[position]
        val urlImage = article.urlToImage
        if (TextUtils.isEmpty(urlImage)) {
            Picasso.get().load(R.drawable.placeholder).into(holder.imageViewArticle)
        } else {
            Picasso.get().load(urlImage).placeholder(R.drawable.placeholder).into(holder.imageViewArticle)
        }
        holder.textViewTitleArticle.text = article.title
        holder.textViewSourceNameArticle.text = article.source?.name ?: ""
        holder.textViewDatePublishedAtArticle.text = getDate(article.publishedAt)
        holder.imageViewButtonMoreArticle.setOnClickListener { v -> Toast.makeText(App.context, article.title, Toast.LENGTH_SHORT).show() }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardViewArticle: CardView = view.findViewById(R.id.article_card_card_for_image)
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
}