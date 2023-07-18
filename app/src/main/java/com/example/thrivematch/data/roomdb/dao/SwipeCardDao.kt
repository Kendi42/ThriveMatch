package com.example.thrivematch.data.roomdb.dao

import androidx.room.*
import com.example.thrivematch.data.models.CardSwipeItemModel
import com.example.thrivematch.data.response.User
import kotlinx.coroutines.flow.Flow

//
//@Dao
//interface SwipeCardDao {
//    // Todo: GEt all cards
//    @Query("SELECT * FROM CardData")
//    fun getAllCards(): Flow<MutableList<CardSwipeItemModel>>
//
//
//    @Insert
//    suspend fun insertCards(cards: CardSwipeItemModel)
//
//    @Delete
//    fun deleteCards(cards: CardSwipeItemModel) // Todo: Shouldn't this delete the whole db
//
//}