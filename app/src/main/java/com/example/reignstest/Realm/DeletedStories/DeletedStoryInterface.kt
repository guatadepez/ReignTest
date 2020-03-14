package com.example.reignstest.Realm.DeletedStories

import io.realm.Realm

/**
 *
 * Created by: GuatadepeZ
 * DeletedStoryInterface : Interface for a more clean connection with realm Database.
 *
 * */

interface DeletedStoryInterface {
    fun addStory(realm: Realm, mStoryClass: DeletedStoryRealm) : Boolean
    fun delAllStories(realm: Realm) : Boolean
}