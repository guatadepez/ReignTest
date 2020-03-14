package com.example.reignstest.Realm.StoryRealm

import android.util.Log
import com.example.reignstest.Class.StoryClass
import com.example.reignstest.Realm.DeletedStories.DeletedStoryRealm
import com.google.gson.Gson
import io.realm.Realm

/**
 * Created by: GuatadepeZ
 * StoryModel: Implementation of the StoryInterface.
 * */

class StoryModel : StoryInterface {

    /**
     *
     * addStory: Add a new Story to the Database.
     *
     * */

    override fun addStory(realm: Realm, mStoryClass: StoryClass): Boolean {

        realm.executeTransaction{
            val mStory = realm.where(DeletedStoryRealm::class.java).equalTo("objectID", mStoryClass.objectID).findFirst()
            if (mStory == null) {
                val mStoryString = Gson().toJson(mStoryClass) as String
                val mStoryRealm = Gson().fromJson(mStoryString, StoryRealm::class.java)
                Log.e("ADDING", mStoryRealm.objectID)
                realm.copyToRealmOrUpdate(mStoryRealm)
            }else{
                Log.d("addStory","THE STORY WAS DELETE BEFORE, CANT BE ADDED AGAIN")
                //do nothing
            }
        }
        return true
    }

    /**
     *
     * editStory: Edit a Story to the Database.
     *
     * */

    override fun editStory(realm: Realm, mStoryClass: StoryClass): Boolean {
        realm.executeTransaction {

            val mStoryString = Gson().toJson(mStoryClass) as String
            val mStoryRealm = Gson().fromJson(mStoryString,
                StoryRealm::class.java)
            realm.copyToRealm(mStoryRealm)

        }
        return true
    }

    /**
     *
     * delStory: del a Story to the Database.
     *
     * */

    override fun delStory(realm: Realm, objectID: String): Boolean {
        realm.executeTransaction {
            var mDeletedStory = DeletedStoryRealm(objectID)
            val mStoryString = Gson().toJson(mDeletedStory) as String
            val mStoryRealm = Gson().fromJson(mStoryString, DeletedStoryRealm::class.java)
            Log.e("DELETING",mStoryRealm.objectID)
            realm.copyToRealmOrUpdate(mStoryRealm)
            realm.where(StoryRealm:: class.java).equalTo("objectID", objectID).findAll().deleteAllFromRealm()
        }
        return true
    }

    /**
     *
     * getAllStories: Get all the Stories in the database and return a MutableCollection of StoryClass.
     *
     * */

    override fun getAllStories(realm: Realm): MutableCollection<StoryClass> {

        var mStories = realm.where(StoryRealm::class.java).findAll() as List<StoryRealm>
        var mStoriesMutable: MutableCollection<StoryClass> = mutableListOf<StoryClass>()
        mStories.forEach{
            var mStory = StoryClass(it.created_at,it.title,it.url,
                it.author,
                it.points,
                it.story_text,
                it.comment_text,
                it.num_comment,
                it.story_id,
                it.story_title,
                it.story_url,
                it.parent_id,
                it.created_at_i,
                it.objectID
            )
            Log.e("AllStories",it.objectID)
            mStoriesMutable.add(mStory)
        }
        realm.close()
        return mStoriesMutable

    }

    /**
     *
     * delAllStories: Delete all the Stories in the database.
     *
     * */

    override fun delAllStories(realm: Realm): Boolean {
        realm.executeTransaction {
            realm.where(StoryRealm:: class.java).findAll().deleteAllFromRealm()
        }
        return true
    }
}