package com.winteryy.readit.data.local.bookstorage

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books WHERE bookSaveStatus='WISH' ORDER BY savedDate DESC LIMIT 20")
    fun getWishBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE bookSaveStatus='WISH' ORDER BY savedDate DESC")
    fun getWishBooksPaging(): PagingSource<Int, BookEntity>

    @Query("SELECT * FROM books WHERE bookSaveStatus='READING' ORDER BY savedDate DESC LIMIT 20")
    fun getReadingBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE bookSaveStatus='READING' ORDER BY savedDate DESC")
    fun getReadingBooksPaging(): PagingSource<Int, BookEntity>

    @Query("SELECT * FROM books WHERE rating>0.0 ORDER BY ratedDate DESC LIMIT 20")
    fun getRatedBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE rating>0.0 ORDER BY ratedDate DESC")
    fun getRatedBooksPaging(): PagingSource<Int, BookEntity>

    @Query("SELECT * FROM books WHERE isbn=:isbn")
    suspend fun getBookByIsbn(isbn: String): BookEntity?

    @Query("SELECT * FROM books WHERE isbn=:isbn")
    fun getBookFlowByIsbn(isbn: String): Flow<BookEntity?>

    @Query("DELETE FROM books WHERE isbn=:isbn")
    suspend fun deleteBookByIsbn(isbn: String)

    @Transaction
    @Query("SELECT books.* FROM books INNER JOIN comments ON books.isbn = comments.bookIsbn")
    fun getBooksHavingCommentPaging(): PagingSource<Int, BookEntity>
}