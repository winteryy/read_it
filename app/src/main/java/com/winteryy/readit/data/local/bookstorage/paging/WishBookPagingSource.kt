package com.winteryy.readit.data.local.bookstorage.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.winteryy.readit.data.local.bookstorage.BookDao
import com.winteryy.readit.data.local.bookstorage.BookEntity
import javax.inject.Inject

class WishBookPagingSource @Inject constructor(
    private val bookDao: BookDao
): PagingSource<Int, BookEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookEntity> {
        TODO("Not yet implemented")
    }

    override fun getRefreshKey(state: PagingState<Int, BookEntity>): Int? {
        TODO("Not yet implemented")
    }

}