package com.softapp.thewallpapertimes534965647.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softapp.thewallpapertimes534965647.FinalWallpaper
import com.softapp.thewallpapertimes534965647.Model.BomModel
import com.softapp.thewallpapertimes534965647.R

class   BomAdapter(val requireContext: Context, val ListBestOfTheMonth: ArrayList<BomModel>) :
    RecyclerView.Adapter<BomAdapter.bomViewHolder>() {

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageview = itemView.findViewById<ImageView>(R.id.bom_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_bom, parent,false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        Glide.with(requireContext).load(ListBestOfTheMonth[position].link).into(holder.imageview);
        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, FinalWallpaper::class.java)
            intent.putExtra("link",ListBestOfTheMonth[position].link)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount()= ListBestOfTheMonth.size

}