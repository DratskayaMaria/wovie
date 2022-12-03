package com.example.wovie.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val pKey: Int,
    @ColumnInfo(name = "movie_id") val movieId: Int
)
