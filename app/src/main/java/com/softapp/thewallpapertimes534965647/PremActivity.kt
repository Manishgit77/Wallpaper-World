package com.softapp.thewallpapertimes534965647

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.softapp.thewallpapertimes534965647.Adapter.CatImgesAdapter
import com.softapp.thewallpapertimes534965647.Model.BomModel
import com.softapp.thewallpapertimes534965647.databinding.ActivityPremBinding
import com.google.firebase.firestore.FirebaseFirestore

class PremActivity : AppCompatActivity() {

    lateinit var binding :ActivityPremBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPremBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.backArrowBtn.setOnClickListener {
            finish()
        }

        val db = FirebaseFirestore.getInstance()
        val uid = intent.getStringExtra("uid")//key abstarct
        val name = intent.getStringExtra("name")//key abstarct


        db.collection("premiumcollection").document(uid!!).collection("wallpaper")
            .addSnapshotListener { value, error ->

                val list0fCatWallpaper = arrayListOf<BomModel>()
                val data = value?.toObjects(BomModel::class.java)
                list0fCatWallpaper.addAll(data!!)

                binding.catTitle.text = name.toString()
//                binding.catCount.text = "${list0fCatWallpaper.size} wallpaper available"

//                binding.catRcv.layoutManager =
//                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//                binding.catRcv.adapter = CatImgesAdapter(this, list0fCatWallpaper)


                binding.catRcv.layoutManager = GridLayoutManager(this, 2)
                binding.catRcv.adapter = CatImgesAdapter(this, list0fCatWallpaper)


            }

        changeStatusBarColor("#000000", "#000000")
    }

    private fun changeStatusBarColor(color1: String, color2: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = android.graphics.Color.parseColor(color1)
            window.navigationBarColor = android.graphics.Color.parseColor(color2)


        }
    }
}