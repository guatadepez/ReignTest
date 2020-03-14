package com.example.reignstest.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import com.example.reignstest.R


/**
 *
 * Created by: GuatadepeZ
 * StoryDetailActivity: Opened when the user clicks on and Item, it opens a WebView with the URL of the Story ONLY if the Phone has Internet connection.
 *
 * */

class StoryDetailActivity : AppCompatActivity() {


    private lateinit var webView: WebView
    companion object{

        const val EXTRA_URL = "url"
        const val EXTRA_TUTLE = "title"
        fun incomingIntent(context: Context, title: String, url:String): Intent {
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

        //actionbar
        val actionbar = supportActionBar
        actionbar!!.title = title
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        webView = findViewById(R.id.wv_storyDetail)
        webView.loadUrl(url)
    }

    //support for backButton on top of the actionbar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
