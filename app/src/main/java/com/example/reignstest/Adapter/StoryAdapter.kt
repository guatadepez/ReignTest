package com.example.reignstest.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reigndevtest.Utils.inflate
import com.example.reignstest.Activity.StoryDetailActivity
import com.example.reignstest.Class.StoryClass
import com.example.reignstest.Realm.StoryModel
import io.realm.Realm
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min

class StoryAdapter(private val mStoryClassList: MutableCollection<StoryClass>) : RecyclerView.Adapter<StoryAdapter.StoryHolder>() {

    var mRealm = Realm.getDefaultInstance()
    var mStoryModel = StoryModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val inflatedView = parent.inflate(com.example.reignstest.R.layout.card_view_test, false)
        return StoryHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mStoryClassList.size
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        val storyClass: StoryClass = mStoryClassList.elementAt(position)
        holder.bind(storyClass)
    }


    fun removeAt(position: Int) {
        mStoryModel.delStory(mRealm,mStoryClassList.elementAt(position).objectID)
        mStoryClassList.remove(mStoryClassList.elementAt(position))
        notifyItemRemoved(position)
    }

    fun update(modelList:MutableCollection<StoryClass>){
        mStoryClassList.addAll(modelList)
        notifyDataSetChanged()
    }


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
            var asdf = getTimeDiff(date!!, currentDate)

            var title : String? = storyClass.story_title
            if(title != null) mTitleView?.text = title
            else mTitleView?.text = storyClass.title

            mDescView?.text = storyClass.author + " - " + asdf
            mUrlView = storyClass.story_url

        }

        private fun getTimeDiff(date1: Date, date2: Date): String {

            val dateDiff = date2.time - date1.time
            val hours = dateDiff / (1000 * 60 * 60)
            val mins = dateDiff / (1000 * 60) % 60
            val diff: String
            if (hours>0) diff = "$hours"+"h"
            else diff = "$mins"+"m"
            return diff
        }

        override fun onClick(v: View) {
            val context = itemView.context
            Log.e("asdf",mUrlView)
            val detailIntent = StoryDetailActivity.IncomingIntent(context, mTitleView!!.text.toString(),mUrlView)
            context.startActivity(detailIntent)
        }
    }





}