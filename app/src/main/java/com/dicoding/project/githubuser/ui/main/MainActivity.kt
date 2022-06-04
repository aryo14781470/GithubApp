package com.dicoding.project.githubuser.ui.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.project.githubuser.*
import com.dicoding.project.githubuser.data.local.PrefHelper
import com.dicoding.project.githubuser.data.model.User
import com.dicoding.project.githubuser.databinding.ActivityMainBinding
import com.dicoding.project.githubuser.ui.detail.DetailUserActivity
import com.dicoding.project.githubuser.ui.favorit.FavoritActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    private val pref by lazy { PrefHelper(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.action_bar_main)

        settingMode()

        adapter = ListAdapter()
        adapter.notifyDataSetChanged()
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@MainActivity, DetailUserActivity::class.java)
                intentToDetail.putExtra(DetailUserActivity.EXTRA_DETAIL, data.login)
                intentToDetail.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intentToDetail.putExtra(DetailUserActivity.EXTRA_GAMBAR, data.avatar_url)
                startActivity(intentToDetail)
            }
        })

        setSearch()

        viewModel.getSearchUsers().observe(this,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun settingMode() {
        when(pref.getBoolean("Dark_Mode")){
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setSearch() {
        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                RVMain.layoutManager = GridLayoutManager(this@MainActivity,2)
            }else{
                RVMain.layoutManager = LinearLayoutManager(this@MainActivity)
            }
            RVMain.setHasFixedSize(true)
            RVMain.adapter = adapter

            IVMainSearch.setOnClickListener{ searchUser() }
            EDMainQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    private fun searchUser(){
        binding.apply {
            val query = EDMainQuery.text.toString()

            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) { binding.PBMain.visibility = View.VISIBLE }
        else{ binding.PBMain.visibility = View.GONE }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_Mode -> {
                startActivity(Intent(this@MainActivity, SettingActivity::class.java))
            }
            R.id.action_Profile ->{
                val menuIntent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(menuIntent)
            }
            R.id.favorit_menu ->{
                val menuIntent = Intent(this@MainActivity, FavoritActivity::class.java)
                Log.d("error", menuIntent.toString())
                startActivity(menuIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}