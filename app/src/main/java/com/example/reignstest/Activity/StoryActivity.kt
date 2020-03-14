package com.example.reignstest.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.reigndevtest.Utils.checkInternetConnection
import com.example.reigndevtest.Volley.APIController
import com.example.reigndevtest.Volley.ServiceVolley
import com.example.reignstest.Adapter.StoryAdapter

import com.example.reignstest.Class.StoryClass
import com.example.reignstest.Gesture.SwipeToDeleteCallback
import com.example.reignstest.R
import com.example.reignstest.Realm.StoryRealm.StoryModel

import com.google.gson.Gson
import io.realm.Realm
import org.json.JSONObject

/**
 *
 * Created by: GuatadepeZ
 * StoryActivity: Main Activity of the App, it shows the list of Item in a RecyclerView, as well as call the instances of the Database and manage the Swipes.
 *
 * */


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefreshLayout = findViewById(R.id.swipeId)
        layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager


        //swipe left to delete implementation
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as StoryAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //swipe down to refresh implementation
        swipeRefreshLayout.setOnRefreshListener {
            loadData()
        }

        //We check if we have Internet connection
        if(checkInternetConnection(this)) { //if we have connection, we launch the call to the api
//            if(!Realm.getDefaultInstance().isClosed) mStoryModel.delAllStories(Realm.getDefaultInstance())
            apiController.get(path, params) { response ->
                val mStories = response!!.getJSONArray("hits")
//                val mStoriesList: MutableCollection<StoryClass> = mutableListOf<StoryClass>()

                for (i in 0 until mStories.length()) {

                    val story = JSONObject(mStories.getString(i))
                    val mRealStory = Gson().fromJson(story.toString(),StoryClass::class.java)

//                    mStoriesList.add(mRealStory)
                    mStoryModel.addStory(Realm.getDefaultInstance(),mRealStory)
                }

                val mStoriesList = mStoryModel.getAllStories(Realm.getDefaultInstance())
                mStoryadapter = StoryAdapter(mStoriesList)
                recyclerView.adapter = mStoryadapter


            }
        }else { //if we do not have connection, we launch the app with data in database.

            val mStoriesList = mStoryModel.getAllStories(Realm.getDefaultInstance())
            mStoryadapter = StoryAdapter(mStoriesList)
            recyclerView.adapter = mStoryadapter
        }

    }

    /**
     *
     * loadData fun: Deal with the swipe to refresh move and load a new set of data via https or use the data in the database according to the internet connection
     *
     * */

    private fun loadData() {

        if(checkInternetConnection(this)){ //If I have connection

//            if(!Realm.getDefaultInstance().isClosed) mStoryModel.delAllStories(Realm.getDefaultInstance()) //we del all the stories in our RealmDB

            apiController.get(path, params) { response ->

                val mStories = response!!.getJSONArray("hits")
//                val mStoriesList: MutableCollection<StoryClass> = mutableListOf<StoryClass>()

                for (i in 0 until mStories.length()) {

                    val story = JSONObject(mStories.getString(i)).toString()
                    val mRealStory = Gson().fromJson(story,StoryClass::class.java)
//                    mStoriesList.add(mRealStory)
                    mStoryModel.addStory(Realm.getDefaultInstance(),mRealStory)
                }

                val mStoriesList = mStoryModel.getAllStories(Realm.getDefaultInstance())
                //we update the view and turn down the refreshLayout
                mStoryadapter.update(mStoriesList)
                swipeRefreshLayout.isRefreshing = false

            }

        }else{ //if we dont have internet, we show a toast so we can not update the list of Stories.
            Toast.makeText(this,"No tienes conexión a internet",Toast.LENGTH_SHORT).show()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
