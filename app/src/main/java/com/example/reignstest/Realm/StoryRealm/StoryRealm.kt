package com.example.reignstest.Realm.StoryRealm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by: GuatadepeZ
 * StoryRealm: Story model in database for Realm.
 * */


open class StoryRealm(
    open var created_at: String = "",
    open var title: String = "",
    open var url: String = "",
    open var author: String = "",
    open var points: String = "",
    open var story_text: String = "",
    open var comment_text: String = "",
    open var num_comment: String = "",
    open var story_id: String = "",
    open var story_title: String = "",
    open var story_url: String = "",
    open var parent_id: String = "",
    open var created_at_i: String = "",
//    open var _tags: String = "",
    @PrimaryKey open var objectID: String = ""
//    open var _highlightResult: String = ""
)
    : RealmObject() {

    fun copy(
        created_at: String = this.created_at,
        title: String = this.title,
        url: String = this.url,
        author: String = this.author,
        points: String = this.points,
        story_text: String = this.story_text,
        comment_text: String = this.comment_text,
        num_comment: String = this.num_comment,
        story_id: String = this.story_id,
        story_title: String = this.story_title,
        story_url: String = this.story_url,
        parent_id: String = this.parent_id,
        created_at_i: String = this.created_at_i,
//        _tags: String = this._tags,
        objectID: String = this.objectID)
//        _highlightResult: String = this._highlightResult)
            = StoryRealm(
        created_at,
        title,
        url,
        author,
        points,
        story_text,
        comment_text,
        num_comment,
        story_id,
        story_title,
        story_url,
        parent_id,
        created_at_i,
        objectID
    )
}