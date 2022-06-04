package com.dicoding.project.githubuser.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.project.githubuser.api.ApiConfigRetrofit
import com.dicoding.project.githubuser.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowing(username: String){
        val client = ApiConfigRetrofit.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                if (response.isSuccessful){ listFollowing.postValue(response.body()) }
            }
            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("onFailure:", t.message.toString())
            }
        })
    }
    fun getFollowing(): LiveData<ArrayList<User>> { return listFollowing }
}