package com.example.thrivematch.data.roomdb.dao

import androidx.room.*
import com.example.thrivematch.data.response.User
import kotlinx.coroutines.flow.Flow

const val ROOM_DB_CURRENT_USER_ID= 0

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewUser(user: User)



    @Query("SELECT * FROM UserData WHERE uid=$ROOM_DB_CURRENT_USER_ID")
    fun getCurrentUser(): User?

    @Query("SELECT * FROM UserData WHERE email = :email")
    suspend fun getUserByEmail(email: String): User

    @Update
    suspend fun updateUser(user: User)

    @Delete
    fun deleteCurrentUser(user: User) // Todo: Shouldn't this delete the whole db

    // Todo: Check if the user set up their account succesfully

}