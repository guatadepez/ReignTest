package com.example.reignstest.SQLite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.reignstest.Class.Story
import java.util.jar.Attributes

class SQLiteHelper(context: Context,
                   factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_STORY_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CREATEDAT +", "
                + COLUMN_STORYID +", "
                + COLUMN_TITLE +", "
                + COLUMN_AUTHOR +", "
                + COLUMN_STYTITLE +", "
                + COLUMN_STORYURL +") ")
        db.execSQL(CREATE_STORY_TABLE)

        val CREATE_DELETED_TABLE = ("CREATE TABLE " +
                TABLE_NAME_DELETED + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CREATEDAT +", "
                +") ")
        db.execSQL(CREATE_DELETED_TABLE)

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DELETED)
        onCreate(db)
    }
    fun addStory(story: Story) {
        val values = ContentValues()
        values.put(COLUMN_STORYID, story.storyId)
        values.put(COLUMN_CREATEDAT, story.createdAt)
        values.put(COLUMN_TITLE, story.title)
        values.put(COLUMN_AUTHOR, story.author)
        values.put(COLUMN_STYTITLE, story.storyTitle)
        values.put(COLUMN_STORYURL, story.storyUrl)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getAllStory(): Cursor? {
        val db = this.readableDatabase
        return  db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "mStoryDB.db"
        val TABLE_NAME = "Story"
        val TABLE_NAME_DELETED = "Delete"
        val COLUMN_ID = "_id"
        val COLUMN_STORYID = "_Storyid"
        val COLUMN_CREATEDAT = "_createdAt"
        val COLUMN_TITLE = "_title"
        val COLUMN_AUTHOR = "_author"
        val COLUMN_STYTITLE = "_storyTitle"
        val COLUMN_STORYURL = "_storyUrl"

    }
}