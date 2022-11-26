package com.example.wovie.db

import androidx.room.RoomDatabase
import com.example.wovie.db.dao.BookmarksDao



abstract class DatabaseService : RoomDatabase() {
    abstract fun bookmarksDao(): BookmarksDao
}