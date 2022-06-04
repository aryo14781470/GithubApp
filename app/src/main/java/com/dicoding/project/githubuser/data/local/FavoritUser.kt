package com.dicoding.project.githubuser.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorit_user")
data class FavoritUser(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String,
)
