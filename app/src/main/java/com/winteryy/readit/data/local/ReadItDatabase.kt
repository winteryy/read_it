package com.winteryy.readit.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.winteryy.readit.data.local.bookstorage.BookDao
import com.winteryy.readit.data.local.bookstorage.BookEntity
import com.winteryy.readit.data.local.commentstorage.CommentDao
import com.winteryy.readit.data.local.commentstorage.CommentEntity

@Database(entities = [BookEntity::class, CommentEntity::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class ReadItDatabase: RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun commentDao(): CommentDao
}