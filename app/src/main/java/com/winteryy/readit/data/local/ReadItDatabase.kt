package com.winteryy.readit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.winteryy.readit.data.local.bookstorage.BookDao
import com.winteryy.readit.data.local.bookstorage.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class ReadItDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
}