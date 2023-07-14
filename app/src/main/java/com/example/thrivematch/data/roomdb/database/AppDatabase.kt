package com.example.thrivematch.data.roomdb.database

import android.accounts.Account
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.thrivematch.data.response.AccountSetupResponse
import com.example.thrivematch.data.response.SignupResponse
import com.example.thrivematch.data.response.User
import com.example.thrivematch.data.roomdb.dao.UserDao


@Database(
    entities = [
        User::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

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
            .build()
    }

}