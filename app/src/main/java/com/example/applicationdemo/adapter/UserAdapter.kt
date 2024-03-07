package com.example.applicationdemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationdemo.databinding.ItemLayoutBinding
import com.example.applicationdemo.models.UsersItem

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserVH>() {

    var planes: List<UsersItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class UserVH(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(UsersItem: UsersItem) {
            binding.name.text = UsersItem.firstName
            binding.email.text = UsersItem.email
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        val binding = ItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserVH(binding)
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bind(planes[position])
    }

    override fun getItemCount(): Int = planes.size
}