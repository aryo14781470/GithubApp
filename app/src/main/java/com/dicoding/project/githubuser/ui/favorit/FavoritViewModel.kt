package com.dicoding.project.githubuser.ui.favorit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.project.githubuser.data.local.FavoritUser
import com.dicoding.project.githubuser.data.local.FavoritUserDao
import com.dicoding.project.githubuser.data.local.UserDatabase

class FavoritViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoritUserDao?
    private var userDb: UserDatabase?
    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoritUserDao()
    }

    fun getFavoritUser(): LiveData<List<FavoritUser>>?{
        return userDao?.getFavoritUser()
    }
}