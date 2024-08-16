package com.winteryy.readit.data.di

import com.winteryy.readit.data.local.bookstorage.BookStorageRepository
import com.winteryy.readit.data.local.bookstorage.impl.BookStorageRepositoryImpl
import com.winteryy.readit.data.remote.search.SearchRepository
import com.winteryy.readit.data.remote.search.impl.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @ViewModelScoped
    abstract fun bindBookStorageRepository(
        bookStorageRepositoryImpl: BookStorageRepositoryImpl
    ): BookStorageRepository
}