package com.erezes.fnder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.erezes.fnder.databinding.FragmentPostViewBinding
import com.erezes.fnder.retrofit.data.ItemsListResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostViewAdapter(private val postList: List<ItemsListResponse.Data>?) :
    RecyclerView.Adapter<PostViewAdapter.PostViewHolder>() {

    inner class PostViewHolder(val binding: FragmentPostViewBinding) : RecyclerView.ViewHolder(binding.root)

    private fun formatData(dateString: String):String{
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy. MM. dd", Locale.getDefault())
            val date: Date = inputFormat.parse(dateString)!!
            return outputFormat.format(date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = FragmentPostViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList?.get(position)
        holder.binding.title.text = post?.title
        holder.binding.date.text = formatData(post!!.createdAt)

        val imageUrl = post.imageUrl
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.binding.image)
    }

    override fun getItemCount(): Int {
        return postList!!.size
    }
}