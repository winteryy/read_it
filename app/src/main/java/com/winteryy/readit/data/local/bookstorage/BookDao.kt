package com.winteryy.readit.data.local.bookstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books WHERE bookSaveStatus='WISH' ORDER BY savedDate DESC")
    fun getWishBooksFlow(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE bookSaveStatus='READING' ORDER BY savedDate DESC")
    fun getReadingBooksFlow(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE rating>0.0 ORDER BY savedDate DESC")
    fun getRatedBooksFlow(): Flow<List<BookEntity>>

    @Query("DELETE FROM books WHERE isbn=:isbn")
    suspend fun deleteBookByIsbn(isbn: String)
}