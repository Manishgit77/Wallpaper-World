package com.softapp.thewallpapertimes534965647.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.softapp.thewallpapertimes534965647.FinalWallpaper
import com.softapp.thewallpapertimes534965647.Model.colortoneModel
import com.softapp.thewallpapertimes534965647.R

class colortoneAdapter(
    val requireContext: Context,
    val listTheColorTone: ArrayList<colortoneModel>
) :
    RecyclerView.Adapter<colortoneAdapter.bomViewHolder>() {

    inner class bomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardBack = itemView.findViewById<CardView>(R.id.item_Card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bomViewHolder {
        return bomViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_colortone, parent,false)
        )
    }

    override fun onBindViewHolder(holder: bomViewHolder, position: Int) {

        val color = listTheColorTone[position].color
        holder.cardBack.setCardBackgroundColor(Color.parseColor(color!!))

        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext, FinalWallpaper::class.java)
            intent.putExtra("link",listTheColorTone[position].link)
            requireContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listTheColorTone.size
    }
}