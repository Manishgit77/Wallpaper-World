package com.softapp.thewallpapertimes534965647.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.softapp.thewallpapertimes534965647.Adapter.BomAdapter
import com.softapp.thewallpapertimes534965647.Adapter.CatAdapter
import com.softapp.thewallpapertimes534965647.Adapter.PremAdapter
import com.softapp.thewallpapertimes534965647.Adapter.colortoneAdapter
import com.softapp.thewallpapertimes534965647.Model.BomModel
import com.softapp.thewallpapertimes534965647.Model.PremModel
import com.softapp.thewallpapertimes534965647.Model.catModel
import com.softapp.thewallpapertimes534965647.Model.colortoneModel
import com.softapp.thewallpapertimes534965647.databinding.FragmentHomeBinding
import com.google.firebase.firestore.FirebaseFirestore



class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private lateinit var db:FirebaseFirestore

    private lateinit var fragmentContext: Context


    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater , container , false)




        db   = FirebaseFirestore.getInstance()

        db.collection("bestofmonth").addSnapshotListener{value ,error ->


            if (value != null) {
                val listBestOfMonth = arrayListOf<BomModel>()
                val data = value?.toObjects(BomModel::class.java)
                listBestOfMonth.addAll(data!!)
                binding.rcvBom.layoutManager= LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rcvBom.adapter = context?.let { BomAdapter(it,listBestOfMonth) }
                // ... rest of your code
            }

//           val  listBestOfMonth = arrayListOf<BomModel>()
//            val data = value?.toObjects(BomModel::class.java)
//            listBestOfMonth.addAll(data!!)

//            binding.rcvBom.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            binding.rcvBom.adapter = BomAdapter(requireContext(),listBestOfMonth)
        }

        db.collection("thecolortone").addSnapshotListener{value ,error ->

            if (value != null) {
                val listTheColorTone = arrayListOf<colortoneModel>()
                val data = value?.toObjects(colortoneModel::class.java)
                listTheColorTone.addAll(data!!)

                binding.rcvTct.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                binding.rcvTct.adapter = context?.let { colortoneAdapter(it, listTheColorTone) }
            }

        }




        db.collection("categories").addSnapshotListener{value ,error ->

            if (value != null) {
                val list0fCategory = arrayListOf<catModel>()
                val data = value?.toObjects(catModel::class.java)
                list0fCategory.addAll(data!!)
                binding.rcvCat.layoutManager = GridLayoutManager(context, 2)
                binding.rcvCat.adapter = context?.let { CatAdapter(it, list0fCategory) }
            }

        }

        db.collection("premiumcollection").addSnapshotListener{value , error ->


            if (value != null) {

                val listOfPremiumCollection = arrayListOf<PremModel>()
                val data = value?.toObjects(PremModel::class.java)
                listOfPremiumCollection.addAll(data!!)



                binding.rcvPremium.layoutManager = GridLayoutManager(context, 2)
                binding.rcvPremium.adapter = context?.let { PremAdapter(it, listOfPremiumCollection) }

            }

        }





        return binding.root
    }





}