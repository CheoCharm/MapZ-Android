package com.cheocharm.data.di

import android.content.Context
import androidx.room.Room
import com.cheocharm.data.local.model.AppDatabase
import com.cheocharm.data.local.model.Converters
import com.cheocharm.data.local.model.GroupDao
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideGroupDao(appDatabase: AppDatabase): GroupDao {
        return appDatabase.groupDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context, gson: Gson): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            DB_NAME
        )
            .addTypeConverter(Converters(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    companion object {
        private const val DB_NAME = "mapz.db"
    }
}
