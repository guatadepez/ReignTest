package com.example.reignstest.Realm.DeletedStories

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


/**
 *
 * Created by: GuatadepeZ
 * DeletedStoryRealm : Realm class to store deleted Story IDs
 *
 * */

open class DeletedStoryRealm(
    @PrimaryKey open var objectID: String = "")
    : RealmObject() {

    fun copy(objectID: String = this.objectID)=
        DeletedStoryRealm(objectID)
}