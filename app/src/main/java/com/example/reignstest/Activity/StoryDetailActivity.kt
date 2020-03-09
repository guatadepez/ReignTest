package com.example.reignstest.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.example.reignstest.R

class StoryDetailActivity : AppCompatActivity() {


    private lateinit var webView: WebView
    companion object{

        const val EXTRA_URL = "url"
        const val EXTRA_TUTLE = "title"
        fun IncomingIntent(context: Context, title: String, url:String): Intent {
            val detailIntent = Intent(context, StoryDetailActivity::class.java)
            detailIntent.putExtra(EXTRA_TUTLE, title)
            detailIntent.putExtra(EXTRA_URL, url)

            return detailIntent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)

        val title = intent.extras?.getString(EXTRA_TUTLE)
        val url = intent.extras?.getString(EXTRA_URL)

        Log.e("asdf",title + ", "+ url)

        setTitle(title)
        webView = findViewById(R.id.wv_storyDetail)
        webView.loadUrl(url)
    }
}
