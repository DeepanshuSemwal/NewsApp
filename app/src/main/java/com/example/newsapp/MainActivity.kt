package com.example.newsapp

import android.app.DownloadManager
import android.app.VoiceInteractor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.lang.reflect.Array.get


class MainActivity : AppCompatActivity(), NewsItemClicked {
    private lateinit var mAdapter:NewsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview:RecyclerView=findViewById(R.id.recycle_view)
        recyclerview.layoutManager=LinearLayoutManager(this)
        fetchdata()
        mAdapter=NewsListAdapter(this)
        recyclerview.adapter=mAdapter





    }
    fun fetchdata() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=d1f395c2d16f48408ff01e7fce17d2c6"
        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                Log.e("sdsadas","$it")
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until  newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )

                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener { error ->

            }
        )

        {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }

        Mysingleton.getInstance(this).addToRequestQueue(getRequest)
    }







    override fun onItemClicked(item: News) {

        val builder =  CustomTabsIntent.Builder()
        val customTabIntent=builder.build()
        customTabIntent.launchUrl(this, Uri.parse(item.url))

    }
}