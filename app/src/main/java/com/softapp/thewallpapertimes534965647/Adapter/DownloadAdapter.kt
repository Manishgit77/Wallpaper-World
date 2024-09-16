package com.softapp.thewallpapertimes534965647.Adapter

import InternalStoragePhoto
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softapp.thewallpapertimes534965647.R



class DownloadAdapter(val requireContext: Context, val ListBestOfTheMonth: List<InternalStoragePhoto>) :
    RecyclerView.Adapter<DownloadAdapter.bomViewHolder>() {

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        //        val imageview = itemView.findViewById<RoundedImageView>(R.id.catImage)
        val imageview = itemView.findViewById<ImageView>(R.id.catImage)
//        val deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_wallpaper11, parent,false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        Glide.with(requireContext).load(ListBestOfTheMonth[position].bmp).into(holder.imageview);

    }


    override fun getItemCount()= ListBestOfTheMonth.size

}