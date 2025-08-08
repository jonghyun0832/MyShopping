package com.example.myshopping.di

import com.example.data.repository.TestRepositoryImpl
import com.example.domain.repository.TestRepository
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
    fun bindTestRepository(testRepositoryImpl: TestRepositoryImpl): TestRepository

//    @Singleton
//    @Provides
//    fun providesAlertLocalSource(source: AlertLocalSourceImpl): AlertLocalSource {
//        return source
//    }
}
