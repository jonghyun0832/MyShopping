package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.BasketDao
import com.example.data.db.dao.LikeDao
import com.example.data.db.dao.PurchaseDao
import com.example.data.db.dao.SearchDao
import com.example.data.db.entity.BasketProductEntity
import com.example.data.db.entity.LikeProductEntity
import com.example.data.db.entity.PurchaseProductEntity
import com.example.data.db.entity.SearchKeywordEntity

@Database(
    entities = [
        PurchaseProductEntity::class,
        LikeProductEntity::class,
        BasketProductEntity::class,
        SearchKeywordEntity::class,
    ],
    version = 3
)
abstract class ApplicationDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "ApplicationDatabase.db"
    }

    abstract fun purchaseDao() : PurchaseDao

    abstract fun likeDao() : LikeDao

    abstract fun basketDao() : BasketDao
    abstract fun searchDao() : SearchDao
}