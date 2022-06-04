package com.dicoding.project.githubuser.ui.favorit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.project.githubuser.R
import com.dicoding.project.githubuser.data.local.FavoritUser
import com.dicoding.project.githubuser.data.model.User
import com.dicoding.project.githubuser.databinding.ActivityFavoritBinding
import com.dicoding.project.githubuser.ui.detail.DetailUserActivity
import com.dicoding.project.githubuser.ui.main.ListAdapter

class FavoritActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritBinding
    private lateinit var adapter: ListAdapter
    private lateinit var viewModel: FavoritViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.action_bar_favorit)

        adapter = ListAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoritViewModel::class.java)

        adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                val intentToDetail = Intent(this@FavoritActivity, DetailUserActivity::class.java)
                intentToDetail.putExtra(DetailUserActivity.EXTRA_DETAIL, data.login)
                intentToDetail.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                intentToDetail.putExtra(DetailUserActivity.EXTRA_GAMBAR, data.avatar_url)
                startActivity(intentToDetail)
            }
        })

        binding.apply {
            RVMain.setHasFixedSize(true)
            RVMain.layoutManager = LinearLayoutManager(this@FavoritActivity)
            RVMain.adapter = adapter
        }

        viewModel.getFavoritUser()?.observe(this,{
            if (it != null){
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoritUser>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users){
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url

            )
            listUser.add(userMapped)
        }
        return listUser
    }
}