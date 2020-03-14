package com.example.reignstest.Realm.DeletedStories

import android.util.Log
import com.google.gson.Gson
import io.realm.Realm

/**
 * Created by: GuatadepeZ
 * DeletedStoryModel : This class is created in case you want to delete the data in DeletedStories Manually.
 *
 * */

class DeletedStoryModel : DeletedStoryInterface{

    override fun addStory(realm: Realm, mStoryClass: DeletedStoryRealm): Boolean {
        realm.executeTransaction {
            val mStoryString = Gson().toJson(mStoryClass) as String
            val mStoryRealm = Gson().fromJson(mStoryString, DeletedStoryRealm::class.java)
            Log.d("DELETED STORY",mStoryRealm.objectID)
            realm.copyToRealmOrUpdate(mStoryRealm)
        }
        return true
    }

    override fun delAllStories(realm: Realm): Boolean {
        realm.executeTransaction {
            realm.where(DeletedStoryRealm :: class.java).findAll().deleteAllFromRealm()
        }
        return true
    }
}