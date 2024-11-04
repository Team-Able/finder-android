package com.erezes.fnder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.erezes.fnder.retrofit.data.SlideItem

class SlideAdapter(private val slides: List<SlideItem>) :
    RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    class SlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.slideImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slide_image, parent, false)
        return SlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val slide = slides[position]
        holder.imageView.setImageResource(slide.imageId)
    }

    override fun getItemCount(): Int = slides.size
}
