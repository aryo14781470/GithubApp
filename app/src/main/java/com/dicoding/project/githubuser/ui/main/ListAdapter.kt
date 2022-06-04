package com.dicoding.project.githubuser.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.project.githubuser.data.model.User
import com.dicoding.project.githubuser.databinding.TempRecyclerViewBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback
    private val list = ArrayList<User>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(user: ArrayList<User>){
        list.clear()
        list.addAll(user)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: TempRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(imageViewTempPhoto)
                textViewTempName.text = user.login
            }

            binding.root.setOnClickListener{
                onItemClickCallback.onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = TempRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}