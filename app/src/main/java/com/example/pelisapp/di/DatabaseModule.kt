package com.example.pelisapp.di


import android.content.Context
import androidx.room.Room
import com.example.pelisapp.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providerDatabase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java,
        "app_database"
    ).build()

    @Provides
    fun provideUserDao(db: AppDatabase) = db.userDao()
    @Provides
    fun provideFavoriteMovieDao(db: AppDatabase) = db.favoriteMovieDao()


}