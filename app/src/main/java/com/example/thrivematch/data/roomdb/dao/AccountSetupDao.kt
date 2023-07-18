package com.example.thrivematch.data.roomdb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.thrivematch.data.models.BusinessModel
import com.example.thrivematch.data.models.InvestorModel
import com.example.thrivematch.data.response.User


@Dao
interface AccountSetupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvestorAccountData(data: InvestorModel)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusinessAccountData(data: BusinessModel)

}