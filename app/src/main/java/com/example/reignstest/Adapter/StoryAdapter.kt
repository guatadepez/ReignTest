package com.example.reignstest.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reigndevtest.Utils.formatTo
import com.example.reigndevtest.Utils.inflate
import com.example.reignstest.Activity.StoryDetailActivity
import com.example.reignstest.Class.Story
import java.text.SimpleDateFormat


class StoryAdapter(private val mStoryList: MutableCollection<Story>) : RecyclerView.Adapter<StoryAdapter.StoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryHolder {
        val inflatedView = parent.inflate(com.example.reignstest.R.layout.card_view_test, false)
        return StoryHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return mStoryList.size
    }

    override fun onBindViewHolder(holder: StoryHolder, position: Int) {
        val story: Story = mStoryList.elementAt(position)
        holder.bind(story)
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
        fun bind(story: Story) {

            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(
                story.createdAt.replace(
                    "Z$".toRegex(),
                    "+0000"
                )
            )

            mTitleView?.text = story.storyTitle
            mDescView?.text = story.author + " - " + date!!.formatTo("dd/M/yyyy hh:mm:ss")
            mUrlView = story.storyUrl

        }

        override fun onClick(v: View) {
            val context = itemView.context
            Log.e("asdf",mUrlView)
            val detailIntent = StoryDetailActivity.IncomingIntent(context, mTitleView!!.text.toString(),mUrlView)
            context.startActivity(detailIntent)
        }
    }

    fun removeAt(position: Int) {
        mStoryList.remove(mStoryList.elementAt(position))
        notifyItemRemoved(position)
    }

    fun update(modelList:MutableCollection<Story>){
        mStoryList.addAll(modelList)
        notifyDataSetChanged()
    }

}