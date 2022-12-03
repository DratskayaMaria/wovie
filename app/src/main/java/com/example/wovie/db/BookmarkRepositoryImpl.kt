package com.example.wovie.db

import com.example.wovie.db.entity.BookmarkEntity
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(database: DatabaseService) : BookmarkRepository {
    private val dao = database.bookmarksDao()

    override suspend fun insertBookmarkedMovie(id: Int) {
        dao.insert(BookmarkEntity(0, id))
    }

    override suspend fun deleteBookmarkedMovie(id: Int) {
        val bookmark = dao.getBookmark(id)
        dao.delete(bookmark)
    }

    override suspend fun findMovieById(id: Int): BookmarkEntity {
        return dao.getBookmark(id)
    }

    override suspend fun deleteAllBookmarks() {
        dao.clear()
    }

    override suspend fun getAllBookmarks(): List<BookmarkEntity> {
        return dao.getAll()
    }
}