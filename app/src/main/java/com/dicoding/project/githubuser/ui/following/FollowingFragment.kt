package com.dicoding.project.githubuser.ui.following

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.project.githubuser.R
import com.dicoding.project.githubuser.data.model.User
import com.dicoding.project.githubuser.databinding.FragmentFollowBinding
import com.dicoding.project.githubuser.ui.detail.DetailUserActivity
import com.dicoding.project.githubuser.ui.main.ListAdapter

class FollowingFragment: Fragment(R.layout.fragment_follow)  {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_DETAIL).toString()

        _binding = FragmentFollowBinding.bind(view)

        adapter = ListAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {  }
        })

        binding.apply {
            RVMain.setHasFixedSize(true)
            RVMain.layoutManager = LinearLayoutManager(activity)
            RVMain.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) { binding.PBMain.visibility = View.VISIBLE }
        else{ binding.PBMain.visibility = View.GONE }
    }

}