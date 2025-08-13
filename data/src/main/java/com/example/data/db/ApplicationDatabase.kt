package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.db.dao.BasketDao
import com.example.data.db.dao.LikeDao
import com.example.data.db.dao.PurchaseDao
import com.example.data.db.entity.BasketProductEntity
import com.example.data.db.entity.LikeProductEntity
import com.example.data.db.entity.PurchaseProductEntity

@Database(
    entities = [
        PurchaseProductEntity::class,
        LikeProductEntity::class,
        BasketProductEntity::class,
    ],
    version = 1
)
abstract class ApplicationDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "ApplicationDatabase.db"
    }

    abstract fun purchaseDao() : PurchaseDao

    abstract fun likeDao() : LikeDao

    abstract fun basketDao() : BasketDao
}