package com.example.newsdesk

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class NewsAdapter(private val listener: NewsItemClicked) : RecyclerView.Adapter<NewsViewHolder>() {
    private val items: ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewHolder = NewsViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.text = currentItem.title

        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

            }

            ).transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.image)

        if (currentItem.description == "null") {
            holder.description.visibility = View.GONE
        } else {
            holder.description.text = currentItem.description
        }

        if (currentItem.author == "null") {
            holder.author.visibility = View.GONE
        } else {
            holder.author.text = currentItem.author
        }

        holder.name.text = currentItem.name

        val formatDate = currentItem.date.subSequence(0, 10)
        holder.date.text = formatDate
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<News>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title)
    val image: ImageView = itemView.findViewById(R.id.imageView)
    val author: TextView = itemView.findViewById(R.id.author)
    val name: TextView = itemView.findViewById(R.id.name)
    val date: TextView = itemView.findViewById(R.id.date)
    val description: TextView = itemView.findViewById(R.id.description)
    val progressBar: ProgressBar = itemView.findViewById(R.id.progress_load_image)

}

interface NewsItemClicked {
    fun onItemClicked(item: News)
}