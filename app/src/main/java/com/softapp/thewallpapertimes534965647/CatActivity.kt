package com.softapp.thewallpapertimes534965647

import android.annotation.SuppressLint
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
import com.softapp.thewallpapertimes534965647.databinding.ActivityCatBinding
import com.google.firebase.firestore.FirebaseFirestore


class CatActivity : AppCompatActivity() {


    lateinit var binding: ActivityCatBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCatBinding.inflate(layoutInflater)
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


        db.collection("categories").document(uid!!).collection("wallpaper")
            .addSnapshotListener { value, error ->

//                if (error != null) {
//                    Log.w(TAG, "Error fetching data: ", error)
//                    // Handle the error appropriately (e.g., display an error message to the user)
//                    return@addSnapshotListener
//                }
//
//                if (value != null) {
//                    // Existing code to process the data...
//
//                    // Add logging for debugging
//                    Log.d(TAG, "Fetched data: " + value.toObjects(BomModel::class.java))
//                }

                if(value != null) {

                    val list0fCatWallpaper = arrayListOf<BomModel>()
                    val data = value?.toObjects(BomModel::class.java)
                    list0fCatWallpaper.addAll(data!!)

                    binding.catTitle.text = name.toString()
//                    binding.catCount.text = "${list0fCatWallpaper.size} wallpaper available"

//                binding.catRcv.layoutManager =
//                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//                binding.catRcv.adapter = CatImgesAdapter(this, list0fCatWallpaper)


                    binding.catRcv.layoutManager = GridLayoutManager(this, 2)
                    binding.catRcv.adapter = CatImgesAdapter(this, list0fCatWallpaper)

                }
            }

        changeStatusBarColor("#000000", "#000000")

    }


//        db.collection("categories").document(uid!!) // Use !! only after null check
//            .collection("wallpaper").
//            .addSnapshotListener { value, error ->
//
//                if (error != null) {
//                    // Handle error (e.g., display error message to user)
//                    Log.w(TAG, "Error fetching wallpapers: ", error)
//                    return@addSnapshotListener
//                }
//
//                if (value == null) {
//                    // Handle the case where no data is retrieved (unlikely, but good practice)
//                    Log.w(TAG, "No wallpaper data found")
//                    return@addSnapshotListener
//                }
//
//
//                val list0fCatWallpaper = arrayListOf<BomModel>()
//                val data = value?.toObjects(BomModel::class.java)
////                val list0fCatWallpaper = arrayListOf<WallpaperModel>()
////                val data = value.toObjects(WallpaperModel::class.java)
//                if (data != null) {
//                    list0fCatWallpaper.addAll(data)
//                }
//
//                binding.catTitle.text = name.toString()
////                binding.catCount.text = getString(R.string.wallpaper_count_format, list0fCatWallpaper.size)
//                binding.catCount.text = "${list0fCatWallpaper.size} wallpaper available"
//                binding.catRcv.layoutManager = GridLayoutManager(this, 2)
//                binding.catRcv.adapter = CatImgesAdapter(this, list0fCatWallpaper)
//            }
//
//        changeStatusBarColor("#000000", "#000000")
//    }


    private fun changeStatusBarColor(color1: String, color2: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = android.graphics.Color.parseColor(color1)
            window.navigationBarColor = android.graphics.Color.parseColor(color2)


        }
    }
}