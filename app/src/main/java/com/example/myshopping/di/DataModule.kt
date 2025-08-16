package com.example.myshopping.di

import com.example.data.repository.CategoryRepositoryImpl
import com.example.data.repository.MainRepositoryImpl
import com.example.data.repository.ProductDetailRepositoryImpl
import com.example.domain.repository.CategoryRepository
import com.example.domain.repository.MainRepository
import com.example.domain.repository.ProductDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    @Singleton
    fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindProductDetailRepository(productDetailRepositoryImpl: ProductDetailRepositoryImpl): ProductDetailRepository
}
