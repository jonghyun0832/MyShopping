package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.ApplicationDatabase
import com.example.data.db.dao.BasketDao
import com.example.data.db.dao.LikeDao
import com.example.data.db.dao.PurchaseHistoryDao
import com.example.data.db.dao.SearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) : ApplicationDatabase {
        return Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            ApplicationDatabase.DB_NAME
        ).fallbackToDestructiveMigration(dropAllTables = false).build()
    }

    @Provides
    @Singleton
    fun provideSearchDao(database: ApplicationDatabase) : SearchDao {
        return database.searchDao()
    }

    @Provides
    @Singleton
    fun provideLikeDao(database: ApplicationDatabase) : LikeDao {
        return database.likeDao()
    }

    @Provides
    @Singleton
    fun provideBasketDao(database: ApplicationDatabase) : BasketDao {
        return database.basketDao()
    }

    @Provides
    @Singleton
    fun providePurchaseHistoryDao(database: ApplicationDatabase) : PurchaseHistoryDao {
        return database.purchaseHistoryDao()
    }
}