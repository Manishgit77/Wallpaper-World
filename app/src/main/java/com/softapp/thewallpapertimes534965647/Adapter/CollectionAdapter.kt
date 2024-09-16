package com.softapp.thewallpapertimes534965647.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softapp.thewallpapertimes534965647.R

class CollectionAdapter(val requireContext: Context, val ListBestOfTheMonth: ArrayList<String> ) :
    RecyclerView.Adapter<CollectionAdapter.bomViewHolder>() {

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
//        val imageview = itemView.findViewById<RoundedImageView>(R.id.catImage)
        val imageview = itemView.findViewById<ImageView>(R.id.catImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_wallpaper, parent,false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        Glide.with(requireContext).load(ListBestOfTheMonth[position]).into(holder.imageview);

        }


    override fun getItemCount()= ListBestOfTheMonth.size

}