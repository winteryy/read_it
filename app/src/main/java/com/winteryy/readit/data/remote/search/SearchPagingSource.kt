package com.winteryy.readit.data.remote.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class SearchPagingSource @Inject constructor(
    private val naverBookApiService: NaverBookApiService,
    private val query: String
): PagingSource<Int, BookItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BookItemResponse> {
        val pageNum = params.key ?: 1
        return try {
            val response = naverBookApiService.searchBooks(
                query = query,
                start = (pageNum - 1) * SEARCH_PAGE_SIZE + 1,
                display = params.loadSize,
                sort = "sim"
            )
            val endOfPage = response.total <= pageNum * SEARCH_PAGE_SIZE

            LoadResult.Page(
                data = response.items,
                prevKey = if(pageNum == 1) null else pageNum-1,
                nextKey = if(endOfPage) null else pageNum+1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, BookItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        const val SEARCH_PAGE_SIZE  = 20
    }

}