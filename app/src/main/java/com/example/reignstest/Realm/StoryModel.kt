package com.example.reignstest.Realm

import android.util.Log
import com.example.reignstest.Class.StoryClass
import com.google.gson.Gson
import io.realm.Realm
import java.util.UUID.randomUUID



class StoryModel : StoryInterface {

    override fun addStory(realm: Realm, mStoryClass: StoryClass): Boolean {
        realm.executeTransaction {

            val mStoryString = Gson().toJson(mStoryClass) as String
            val mStoryRealm = Gson().fromJson(mStoryString, StoryRealm::class.java)
            Log.e("info",mStoryRealm.objectID)
            realm.copyToRealmOrUpdate(mStoryRealm)

        }
        return true

//
//        val mStoryString = Gson().toJson(mStoryClass) as String
//        val mStoryRealm = Gson().fromJson(mStoryString, StoryRealm::class.java)
//
//        try {
//            realm.beginTransaction()
//            realm.copyToRealmOrUpdate(mStoryRealm)
//            realm.commitTransaction()
//            realm.close()
//            return true
//
//        } catch (e: Exception) {
//            println(e)
//            return false
//        }
    }

    override fun editStory(realm: Realm, mStoryClass: StoryClass): Boolean {
        realm.executeTransaction {

            val mStoryString = Gson().toJson(mStoryClass) as String
            val mStoryRealm = Gson().fromJson(mStoryString,StoryRealm::class.java)
            realm.copyToRealm(mStoryRealm)

        }

        return true

//
//        val mStoryString = Gson().toJson(mStoryClass) as String
//        val mStoryRealm = Gson().fromJson(mStoryString,StoryRealm::class.java)
//        try {
//            realm.beginTransaction()
//            realm.copyToRealm(mStoryRealm)
//            realm.commitTransaction()
//            realm.close()
//            return true
//        } catch (e: Exception) {
//            println(e)
//            return false
//        }
    }
    override fun delStory(realm: Realm, objectID: String): Boolean {
        realm.executeTransaction {
            realm.where(StoryRealm :: class.java).equalTo("objectID", objectID).findAll().deleteAllFromRealm()
        }
        return true
//        try {
//            realm.beginTransaction()
//            realm.where(StoryRealm :: class.java).equalTo("objectID", objectID).findAll().deleteAllFromRealm()
//            realm.commitTransaction()
//            realm.close()
//            Log.e("DELETING","COMPLETE!!!!")
//            return true
//        } catch (e: Exception) {
//            println(e)
//            return false
//        }
    }

//    override fun getStory(realm: Realm, objectID: String): StoryClass {
//        var mStoryRealm = realm.where(StoryRealm::class.java).equalTo("objectID", objectID).findFirst()!!
//        realm.close()
//        var mStoryClass = Gson().fromJson(Gson().toJson(mStoryRealm),StoryClass::class.java)
//        return mStoryClass
//    }
//
    override fun getAllStories(realm: Realm): MutableCollection<StoryClass> {

        var mStories = realm.where(StoryRealm::class.java).findAll() as List<StoryRealm>
        var mStoriesMutable: MutableCollection<StoryClass> = mutableListOf<StoryClass>()
        var mStoriesClase: StoryClass
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
            Log.e("DBDATA",mStory.toString())

            var mRow = mStory.toString()

//            mStoriesClase = Gson().fromJson(mRow,StoryClass::class.java)
            mStoriesMutable.add(mStory)
        }
        realm.close()
        return mStoriesMutable

    }
}