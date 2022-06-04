package com.dicoding.project.githubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoritUserDao {
    @Insert
    suspend fun addFavorit(favoritUser: FavoritUser)

    @Query("SELECT * FROM favorit_user")
    fun getFavoritUser(): LiveData<List<FavoritUser>>


    @Query("SELECT count(*) FROM favorit_user WHERE favorit_user.id = :id")
    suspend fun checkUser(id: Int):Int


    @Query("DELETE FROM favorit_user WHERE favorit_user.id = :id")
    suspend fun removeFromfavorit(id: Int):Int
}