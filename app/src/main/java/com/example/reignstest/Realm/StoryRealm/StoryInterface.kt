package com.example.reignstest.Realm.StoryRealm

import com.example.reignstest.Class.StoryClass
import io.realm.Realm


/**
 *
 * Created by: GuatadepeZ
 * StoryInterface: Interface Used to communicate with the Realm Database in a more clean way.
 *
 * */

interface StoryInterface {
    fun addStory(realm: Realm,mStoryClass: StoryClass) : Boolean
    fun delStory(realm: Realm, objectID: String) : Boolean
    fun editStory(realm: Realm, mStoryClass: StoryClass) : Boolean
    fun getAllStories(realm: Realm) : MutableCollection<StoryClass>
    fun delAllStories(realm: Realm) : Boolean
}