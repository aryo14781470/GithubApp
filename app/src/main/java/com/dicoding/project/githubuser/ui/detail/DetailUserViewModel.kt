package com.dicoding.project.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.project.githubuser.api.ApiConfigRetrofit
import com.dicoding.project.githubuser.data.local.FavoritUser
import com.dicoding.project.githubuser.data.local.FavoritUserDao
import com.dicoding.project.githubuser.data.local.UserDatabase
import com.dicoding.project.githubuser.data.model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoritUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoritUserDao()
    }

    fun setUserDetail(username: String) {
        val client = ApiConfigRetrofit.getApiService().getUsersDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                if (response.isSuccessful){user.postValue(response.body())}
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("onFailure:", t.message.toString())
            }
        })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> { return user }

    fun addToFavorit(username: String, id: Int, avatar_url: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoritUser(username, id, avatar_url)
            userDao?.addFavorit(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorit(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromfavorit(id)
        }
    }
}