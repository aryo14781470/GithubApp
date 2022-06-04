package com.dicoding.project.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.project.githubuser.R
import com.dicoding.project.githubuser.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_GAMBAR = "extra_gambar"
    }
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var binding: ActivityDetailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.action_bar_detail)

        val username = intent.getStringExtra(EXTRA_DETAIL)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_GAMBAR)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        val bundle = Bundle()
        bundle.putString(EXTRA_DETAIL, username)

        if (username != null) { viewModel.setUserDetail(username) }

        viewModel.getUserDetail().observe(this, {
            showLoading(true)
            if (it != null){
                binding.apply {
                    textViewDetailName.text = it.name
                    textViewDetailUsername.text = it.login
                    textViewDetailLink.text = it.html_url

                    if(it.location.isNullOrEmpty()) { textViewDetailLocation.text = getString(R.string.dummy_null)
                    }else { textViewDetailLocation.text = it.location }

                    if(it.company.isNullOrEmpty()) { textViewDetailCompany.text = getString(R.string.dummy_null)
                    }else { textViewDetailCompany.text = it.company }

                    if(it.public_repos != null) { textViewDetailRepository.text = "${it.public_repos} Repositories"
                    }else { textViewDetailRepository.text = "0 Repositories" }

                    if(it.public_gists != null) { textViewDetailGist.text = "${it.public_gists} Gist"
                    }else { textViewDetailGist.text = "0 Gist" }

                    if(it.followers != null) { textViewDetailFollowers.text = "${it.followers} Followers"
                    }else { textViewDetailFollowers.text = "0 Followers" }

                    if(it.following != null) { textViewDetailFollowing.text = "${it.following} Following"
                    }else { textViewDetailFollowing.text = "0 Following" }

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .into(imageViewDetailPhoto)
                }
            }
            showLoading(false)
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count =viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.toogleFavorit.isChecked = true
                        _isChecked = true
                    }else{
                        binding.toogleFavorit.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toogleFavorit.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                if (username != null) {
                    if (avatarUrl != null) {
                        viewModel.addToFavorit(username, id, avatarUrl)
                    }
                }
            }else{
                viewModel.removeFromFavorit(id)
            }
            binding.toogleFavorit.isChecked = _isChecked
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            VPDetail.adapter = sectionPagerAdapter
            TBDetail.setupWithViewPager(VPDetail)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) { binding.progressBar.visibility = View.VISIBLE }
        else{ binding.progressBar.visibility = View.GONE }
    }

}