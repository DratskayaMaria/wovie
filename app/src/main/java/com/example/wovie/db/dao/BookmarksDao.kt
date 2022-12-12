package com.example.wovie.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wovie.db.entity.BookmarkEntity


@Dao
interface BookmarksDao {
    @Insert
    suspend fun insert(bookmark: BookmarkEntity)

    @Delete
    suspend fun delete(bookmark: BookmarkEntity)

    @Query("SELECT * FROM bookmarks WHERE movie_id=:id")
    suspend fun getBookmark(id: Int): BookmarkEntity?

    @Query("SELECT * FROM bookmarks")
    suspend fun getAll(): List<BookmarkEntity>

    @Query("DELETE FROM bookmarks")
    suspend fun clear()
}