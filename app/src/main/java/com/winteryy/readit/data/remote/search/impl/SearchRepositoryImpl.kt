package com.winteryy.readit.data.remote.search.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.winteryy.readit.R
import com.winteryy.readit.data.RemoteError
import com.winteryy.readit.data.Result
import com.winteryy.readit.data.remote.search.NaverBookApiService
import com.winteryy.readit.data.remote.search.SearchRepository
import com.winteryy.readit.data.remote.search.paging.SearchPagingSource
import com.winteryy.readit.data.remote.search.paging.SearchPagingSource.Companion.SEARCH_PAGE_SIZE
import com.winteryy.readit.model.Book
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val naverBookApiService: NaverBookApiService,
    private val connectivityManager: ConnectivityManager,
    @ApplicationContext private val context: Context
) : SearchRepository {
    override suspend fun searchBooks(
        query: String,
        start: Int,
        display: Int,
        sort: String
    ): Result<List<Book>> {
        return withContext(Dispatchers.IO) {
            try {
                val searchBookList = naverBookApiService.searchBooks(
                    query = query,
                    start = start,
                    display = display,
                    sort = sort
                ).items.map { bookItemResponse ->
                    bookItemResponse.toBook()
                }
                Result.Success(searchBookList)
            } catch (e: Exception) {
                Result.Error(
                    RemoteError.NetworkError(context.getString(R.string.network_error_search_fail))
                )
            }
        }
    }

    override fun getSearchPagingData(query: String): Result<Flow<PagingData<Book>>> {
        return if(isNetworkAvailable()) {
            try {
                Result.Success(
                    Pager(
                        config = PagingConfig(
                            pageSize = SEARCH_PAGE_SIZE,
                            enablePlaceholders = false,
                            maxSize = SEARCH_PAGE_SIZE * 3
                        ),
                        pagingSourceFactory = { SearchPagingSource(naverBookApiService, query) }
                    ).flow
                        .map { pagingData ->
                            pagingData.map { bookResponse ->
                                bookResponse.toBook()
                            }
                        }
                )
            } catch (e: Exception) {
                Result.Error(
                    RemoteError.NetworkError(context.getString(R.string.network_error_search_fail))
                )
            }
        } else {
            Result.Error(
                RemoteError.NetworkError(context.getString(R.string.network_error_no_connection))
            )
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}