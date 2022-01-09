package com.example.newsapp

import android.annotation.SuppressLint
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter( private val listner: NewsItemClicked):
    RecyclerView.Adapter<NewsViewHolder>() {

    private val items: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):NewsViewHolder {

        val view=LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        val viewholder=NewsViewHolder(view)
        view.setOnClickListener {
        listner.onItemClicked(items[viewholder.adapterPosition])

        }
       return viewholder

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val CurItem=items[position]
        holder.titleview.text=CurItem.title
        holder.author.text=CurItem.author

        Glide.with(holder.itemView.context).load(CurItem.imageurl).into(holder.image)


    }

    override fun getItemCount(): Int {

       return items.size
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateNews(updateNews:ArrayList<News>)
    {
        items.clear()
        items.addAll(updateNews)

        notifyDataSetChanged()

    }

}
class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
{
   val titleview:TextView=itemView.findViewById(R.id.textView)
    val image:ImageView=itemView.findViewById(R.id.image_view)
    val author:TextView=itemView.findViewById(R.id.textView2)
}

interface NewsItemClicked
{
    fun onItemClicked(item:News)
}
