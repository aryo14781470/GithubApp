package com.dicoding.project.githubuser.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.project.githubuser.api.ApiConfigRetrofit
import com.dicoding.project.githubuser.data.model.User
import com.dicoding.project.githubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>>()

    fun setSearchUsers(query: String){
        val client = ApiConfigRetrofit.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful){ listUser.postValue(response.body()?.items) }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("onFailure:", t.message.toString())
            }
        })
    }
    fun getSearchUsers(): LiveData<ArrayList<User>> { return listUser }
}