package com.example.thrivematch.data.roomdb.database

import android.accounts.Account
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.response.AccountSetupResponse
import com.example.thrivematch.data.response.SignupResponse
import com.example.thrivematch.data.response.User
import com.example.thrivematch.data.roomdb.Converts
import com.example.thrivematch.data.roomdb.dao.AccountSetupDao
import com.example.thrivematch.data.roomdb.dao.SwipeCardDao
//import com.example.thrivematch.data.roomdb.dao.SwipeCardDao
import com.example.thrivematch.data.roomdb.dao.UserDao


@Database(
    entities = [
        User::class,
        InvestorModel:: class,
        BusinessModel::class,
        CardSwipeItemModel::class
    ],
    version = 8,
    exportSchema = false
)
@TypeConverters(Converts::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun swipeCardDao(): SwipeCardDao
    abstract fun accountSetupDao(): AccountSetupDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(Any()) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "thrivematch-database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}