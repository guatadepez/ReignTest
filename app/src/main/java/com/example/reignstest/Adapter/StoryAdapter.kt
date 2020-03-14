package com.example.reignstest.Adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.reigndevtest.Utils.checkInternetConnection
import com.example.reigndevtest.Utils.inflate
import com.example.reignstest.Activity.StoryDetailActivity
import com.example.reignstest.Class.StoryClass
import com.example.reignstest.Realm.StoryRealm.StoryModel
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * Created by: GuatadepeZ
 * StoryAdapter to manage the items of the recycler view,the viewHolder, the onItemClick and the remove Item from the list and the Database.
 *
 * */


class StoryAdapter(private val mStoryClassList: MutableCollection<StoryClass>) : RecyclerView.Adapter<StoryAdapter.StoryHolder>() {

    var mStoryModel = StoryModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val inflatedView = parent.inflate(com.example.reignstest.R.layout.card_view_story, false)
        return StoryHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mStoryClassList.size
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        val storyClass: StoryClass = mStoryClassList.elementAt(position)
        holder.bind(storyClass)
    }

    /**
     *
     * remoteAt: function that is trigger whenever the User delete an Item.
     * */
    fun removeAt(position: Int) {
        mStoryModel.delStory(Realm.getDefaultInstance(),mStoryClassList.elementAt(position).objectID)
        mStoryClassList.remove(mStoryClassList.elementAt(position))
        notifyItemRemoved(position)
    }

    /**
     *
     * update: Update the recycler view.
     *
     * */
    fun update(modelList:MutableCollection<StoryClass>){
        mStoryClassList.clear()
        mStoryClassList.addAll(modelList)
        notifyDataSetChanged()
    }

    /**
     *
     * ViewHolder.
     *
     * */

    class StoryHolder (v: View)  : RecyclerView.ViewHolder(v), View.OnClickListener{

        private var mTitleView: TextView? = null
        private var mDescView: TextView? = null
        private var mUrlView: String = ""

        init {
            v.setOnClickListener(this)
            mTitleView = itemView.findViewById(com.example.reignstest.R.id.tv_storyTitle)
            mDescView = itemView.findViewById(com.example.reignstest.R.id.tv_StoryAuthorAndCreateAt)
        }
        @SuppressLint("SetTextI18n")
        fun bind(storyClass: StoryClass) {

            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(
                storyClass.created_at.replace(
                    "Z$".toRegex(),
                    "+0000"
                )
            )

            val currentDate = Calendar.getInstance().time
            val mDateDiff = getTimeDiff(date!!, currentDate)

            val title : String? = storyClass.story_title
            if(title != null && title != "") mTitleView?.text = title  //we use title or story_title
            else mTitleView?.text = storyClass.title

            mDescView?.text = storyClass.author + " - " + mDateDiff
            mUrlView = storyClass.story_url

        }

        /**
         *
         * getTimeDiff: function to get the time differences between two dates. In this case returns the hour if it is greater than 0 else return the minutes.
         *
         * */

        private fun getTimeDiff(date1: Date, date2: Date): String {

            val dateDiff = date2.time - date1.time
            val hours = dateDiff / (1000 * 60 * 60)
            val mins = dateDiff / (1000 * 60) % 60
            val diff: String
            if (hours>0) diff = "$hours"+"h"
            else diff = "$mins"+"m"
            return diff
        }

        override fun onClick(v: View) { //onclicklestener
            val context = itemView.context
            if(checkInternetConnection(context)){ //if we have internet, we launch the webview
                val detailIntent = StoryDetailActivity.incomingIntent(context, mTitleView!!.text.toString(),mUrlView)
                context.startActivity(detailIntent)
            }else{ //else we tell the user that he cannot open the webview without internet.
                Toast.makeText(context,"No tienes internet, no puedes abrir la página web.",Toast.LENGTH_SHORT).show()
            }
        }
    }

}