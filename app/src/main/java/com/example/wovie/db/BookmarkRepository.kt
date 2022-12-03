package com.example.wovie.db

import com.example.wovie.db.entity.BookmarkEntity

interface BookmarkRepository {
    suspend fun insertBookmarkedMovie(id: Int)
    suspend fun deleteBookmarkedMovie(id: Int)
    suspend fun findMovieById(id: Int): BookmarkEntity
    suspend fun deleteAllBookmarks()
    suspend fun getAllBookmarks(): List<BookmarkEntity>
}