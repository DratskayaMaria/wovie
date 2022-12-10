package com.example.wovie.di

import android.content.Context
import androidx.room.Room
import com.example.wovie.api.ApiService
import com.example.wovie.db.BookmarkRepository
import com.example.wovie.db.BookmarkRepositoryImpl
import com.example.wovie.db.DatabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiService() = ApiService.getInstance()

    @Provides
    fun provideLocalDb(@ApplicationContext context: Context): DatabaseService {
        return Room.databaseBuilder(context, DatabaseService::class.java, "imdb-clone-db")
            .build()
    }

    @Provides
    fun provideBookmarkRepository(databaseService: DatabaseService) : BookmarkRepository {
        return BookmarkRepositoryImpl(databaseService)
    }

}