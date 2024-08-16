package com.winteryy.readit.data.di

import android.content.Context
import androidx.room.Room
import com.winteryy.readit.data.local.ReadItDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideReadItDatabase(
        @ApplicationContext context: Context
    ): ReadItDatabase {
        return Room.databaseBuilder(
            context,
            ReadItDatabase::class.java,
            "readit_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideBookDao(readItDatabase: ReadItDatabase) = readItDatabase.bookDao()
}