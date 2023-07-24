package com.example.thrivematch.data.roomdb.dao

import androidx.room.*
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.response.User
import kotlinx.coroutines.flow.Flow


@Dao
interface SwipeCardDao {
    @Query("SELECT * FROM CardData")
    fun getAllCards(): Flow<MutableList<CardSwipeItemModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CardSwipeItemModel>)

}