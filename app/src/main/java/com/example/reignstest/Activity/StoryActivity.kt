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

import com.example.reignstest.Class.Story
import com.example.reignstest.Gesture.SwipeToDeleteCallback
import com.example.reignstest.R

import com.example.reignstest.SQLite.SQLiteHelper
import org.json.JSONObject

class StoryActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mStoryadapter: StoryAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val path = "https://hn.algolia.com/api/v1/search_by_date?query=android"
    val params = JSONObject()
    val service = ServiceVolley()
    val apiController = APIController(service)

    val dbHandler = SQLiteHelper(this, null)
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
                var title: String
                var storyId: Int
                var storyCA: String
                var author: String
                var storytitle: String
                var storyurl: String
                val mStoriesList: MutableCollection<Story> = mutableListOf<Story>()

                for (i in 0 until mStories.length()) {
                    val story = JSONObject(mStories.getString(i))
                    Log.e("story", story.toString())

                    if (!story.get("story_id").equals(null)) storyId = story.getInt("story_id")
                    else if (!story.get("parent_id").equals(null)) storyId =
                        story.getInt("parent_id")
                    else storyId = story.getInt("objectID")
                    if (!story.get("created_at").equals(null)) storyCA =
                        story.get("created_at") as String
                    else storyCA = "null"
                    if (!story.get("title").equals(null)) title = story.get("title") as String
                    else if (!story.get("story_title").equals(null)) title =
                        story.get("story_title") as String
                    else title = "No Title"
                    if (!story.get("author").equals(null)) author = story.get("author") as String
                    else author = "null"
                    if (!story.get("story_title").equals(null)) storytitle =
                        story.get("story_title") as String
                    else if (!story.get("title").equals(null)) storytitle =
                        story.get("title") as String
                    else storytitle = "No Title"
                    if (!story.get("story_url").equals(null)) storyurl =
                        story.get("story_url") as String
                    else storyurl = "null"


                    val mDBStory = Story(
                        storyId,
                        storyCA, title, author,
                        storytitle, storyurl
                    )
                    mStoriesList.add(mDBStory)
                    dbHandler.addStory(mDBStory)
                }

                mStoryadapter = StoryAdapter(mStoriesList)
                recyclerView.adapter = mStoryadapter


            }
        }else { //todo
//            OFFLINE IMPLEMENTATION
        }

    }

    fun cargarDatos() {

        apiController.get(path, params) { response ->

            val mStories = response!!.getJSONArray("hits")
            var title: String
            var storyId: Int
            var storyCA: String
            var author: String
            var storytitle: String
            var storyurl: String
            val mStoriesList: MutableCollection<Story> = mutableListOf<Story>()

            for (i in 0 until mStories.length()) {
                val story = JSONObject(mStories.getString(i))
                Log.e("story",story.toString())

                if (!story.get("story_id").equals(null)) storyId = story.getInt("story_id")
                else if(!story.get("parent_id").equals(null)) storyId = story.getInt("parent_id")
                else storyId = story.getInt("objectID")
                if (!story.get("created_at").equals(null)) storyCA = story.get("created_at") as String
                else storyCA = "null"
                if (!story.get("title").equals(null)) title  = story.get("title") as String
                else if (!story.get("story_title").equals(null)) title = story.get("story_title") as String
                else title = "No Title"
                if (!story.get("author").equals(null)) author = story.get("author") as String
                else author = "null"
                if (!story.get("story_title").equals(null)) storytitle = story.get("story_title") as String
                else if (!story.get("title").equals(null)) storytitle = story.get("title") as String
                else storytitle = "No Title"
                if (!story.get("story_url").equals(null)) storyurl = story.get("story_url") as String
                else storyurl = "null"


                val mDBStory = Story(storyId,
                    storyCA, title, author,
                    storytitle, storyurl
                )
                mStoriesList.add(mDBStory)
                dbHandler.addStory(mDBStory)
            }

            mStoryadapter.update(mStoriesList)
            swipeRefreshLayout.isRefreshing = false

        }
    }
}
