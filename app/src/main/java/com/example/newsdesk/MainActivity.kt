package com.example.newsdesk

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity(), NewsItemClicked, View.OnClickListener {

    private lateinit var mAdapter: NewsAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var headline: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<RecyclerView>(R.id.recycler_view)
        view.layoutManager = LinearLayoutManager(this)

        fetchData("")
        queryCalls()

        mAdapter = NewsAdapter(this)
        view.adapter = mAdapter

        progressBar = findViewById(R.id.progressBar_main)
        progressBar.visibility = View.VISIBLE
        headline = findViewById(R.id.headlines)
        headline.visibility = View.GONE

    }

    private fun fetchData(query: String) {

        Volley.newRequestQueue(this)
        val url = "https://olds-server.cyclic.app/$query"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            {
                progressBar.visibility = View.GONE
                headline.visibility = View.VISIBLE
                val newsArray = ArrayList<News>()
                for (i in 0 until it.length()) {
                    val newsJsonObject = it.getJSONObject(i)
                    val name = newsJsonObject.getJSONObject("source")
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("publishedAt"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage"),
                        name.getString("name")

                    )
                    newsArray.add(news)
                    mAdapter.updateNews(newsArray)

                }
            },
            {
                it.printStackTrace()

            })
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)


    }


    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder().build()
        builder.launchUrl(this, Uri.parse(item.url))
    }

    private fun queryCalls() {

        val home = findViewById<LinearLayout>(R.id.home_page)
        val tech = findViewById<LinearLayout>(R.id.tech)
        val buss = findViewById<LinearLayout>(R.id.business)
        val sports = findViewById<LinearLayout>(R.id.sports)
        val entertainment = findViewById<LinearLayout>(R.id.entertainment)


        home.setOnClickListener(this@MainActivity)
        tech.setOnClickListener(this@MainActivity)
        buss.setOnClickListener(this@MainActivity)
        sports.setOnClickListener(this@MainActivity)
        entertainment.setOnClickListener(this@MainActivity)


    }

    override fun onClick(v: View?) {
        progressBar.visibility = View.VISIBLE

        when(v!!.id){
           R.id.home_page ->  fetchData("")
           R.id.tech ->  fetchData("tech")
           R.id.business->  fetchData("business")
           R.id.sports ->  fetchData("sports")
           R.id.entertainment ->  fetchData("ent")
        }
    }

}



