package com.example.reignstest.Realm

import com.example.reignstest.Class.StoryClass
import io.realm.Realm

interface StoryInterface {
    fun addStory(realm: Realm,mStoryClass: StoryClass) : Boolean
    fun delStory(realm: Realm, objectID: String) : Boolean
    fun editStory(realm: Realm, mStoryClass: StoryClass) : Boolean
//    fun getStory(realm: Realm, objectID: String) : StoryClass
    fun getAllStories(realm: Realm) : MutableCollection<StoryClass>
}