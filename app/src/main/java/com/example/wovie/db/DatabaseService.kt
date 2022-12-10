package com.example.wovie.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wovie.db.dao.BookmarksDao
import com.example.wovie.db.entity.BookmarkEntity


@Database(entities = [BookmarkEntity::class], exportSchema = false, version = 1)
abstract class DatabaseService : RoomDatabase() {
    abstract fun bookmarksDao(): BookmarksDao
}