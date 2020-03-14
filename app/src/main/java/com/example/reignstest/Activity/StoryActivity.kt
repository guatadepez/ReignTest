package com.example.reignstest.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.reigndevtest.Volley.APIController
import com.example.reigndevtest.Volley.ServiceVolley
import com.example.reignstest.Adapter.StoryAdapter

import com.example.reignstest.Class.StoryClass
import com.example.reignstest.Gesture.SwipeToDeleteCallback
import com.example.reignstest.R
import com.example.reignstest.Realm.StoryModel

import com.google.gson.Gson
import io.realm.Realm
import org.json.JSONObject
import java.util.*

class StoryActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mStoryadapter: StoryAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val path = "https://hn.algolia.com/api/v1/search_by_date?query=android"
    val params = JSONObject()
    val service = ServiceVolley()
    val apiController = APIController(service)
    var mStoryModel = StoryModel()
    var mRealm = Realm.getDefaultInstance()
    var mConnection : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipeId)
        layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as StoryAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        swipeRefreshLayout.setOnRefreshListener {
            cargarDatos()
        }

//        mConnection = isInternetAvailable()

        if(mConnection) {
            apiController.get(path, params) { response ->
                val mStories = response!!.getJSONArray("hits")
                val mStoriesList: MutableCollection<StoryClass> = mutableListOf<StoryClass>()

                for (i in 0 until mStories.length()) {

                    val story = JSONObject(mStories.getString(i))
                    val mRealStory = Gson().fromJson(story.toString(),StoryClass::class.java)

                    mStoriesList.add(mRealStory)
                    mStoryModel.addStory(mRealm,mRealStory)
                }

                mStoryadapter = StoryAdapter(mStoriesList)
                recyclerView.adapter = mStoryadapter


            }
        }else { //todo
            mStoryadapter = StoryAdapter(mStoryModel.getAllStories(mRealm))
            recyclerView.adapter = mStoryadapter
        }

    }

    fun cargarDatos() {

        apiController.get(path, params) { response ->

            val mStories = response!!.getJSONArray("hits")
            val mStoriesList: MutableCollection<StoryClass> = mutableListOf<StoryClass>()

            for (i in 0 until mStories.length()) {

                val story = JSONObject(mStories.getString(i)).toString()
                val mRealStory = Gson().fromJson(story,StoryClass::class.java)
                mStoriesList.add(mRealStory)
                mStoryModel.addStory(mRealm,mRealStory)
            }

//            mStoryadapter = StoryAdapter(mStoriesList)
//            recyclerView.adapter = mStoryadapter

            mStoryadapter.update(mStoriesList)
            swipeRefreshLayout.isRefreshing = false

        }
    }
}
