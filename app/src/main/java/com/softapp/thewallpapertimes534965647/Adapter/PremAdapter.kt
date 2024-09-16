package com.softapp.thewallpapertimes534965647.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softapp.thewallpapertimes534965647.Model.PremModel
import com.softapp.thewallpapertimes534965647.PremActivity
import com.softapp.thewallpapertimes534965647.R

class PremAdapter(val  requireContext : Context, val listOfPremiumCollection : ArrayList<PremModel>):RecyclerView.Adapter<PremAdapter.PremViewHolder>(){

    inner class PremViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val prem_image =itemView.findViewById<ImageView>(R.id.prem_image)
        val prem_name = itemView.findViewById<TextView>(R.id.prem_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PremAdapter.PremViewHolder {
        return PremViewHolder(
            LayoutInflater.from(requireContext).inflate(R.layout.item_prem, parent,   false)
        )
    }

    override fun onBindViewHolder(holder: PremAdapter.PremViewHolder, position: Int) {
        holder.prem_name.text = listOfPremiumCollection[position].name
        Glide.with(requireContext).load(listOfPremiumCollection[position].link).into(holder.prem_image)
        holder.itemView.setOnClickListener{
            val intent = Intent(requireContext , PremActivity::class.java)
            intent.putExtra("uid", listOfPremiumCollection[position].id)
            intent.putExtra("name", listOfPremiumCollection[position].name)
            requireContext.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
           return listOfPremiumCollection.size
    }





}
