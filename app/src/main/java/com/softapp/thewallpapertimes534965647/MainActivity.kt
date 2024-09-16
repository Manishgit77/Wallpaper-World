package com.softapp.thewallpapertimes534965647


import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdView
import com.softapp.thewallpapertimes534965647.Fragments.DownloadFragment
import com.softapp.thewallpapertimes534965647.Fragments.HomeFragment
import com.softapp.thewallpapertimes534965647.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private var readPermissionGranted = false
    private var writePermissionGranted = false
    private lateinit var permissionsLauncher:ActivityResultLauncher<Array<String>>


    lateinit var mAdView :AdView

    private lateinit var currentFragment: Fragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        changeStatusBarColor("#000000","#000000")

        replaceFragment(HomeFragment())





        binding.icHome.setOnClickListener {
            replaceFragment(HomeFragment())
            binding.homeFilledBtn.setImageResource(R.drawable.home_filled2)
            binding.downloadBtn.setImageResource(R.drawable.arrow_down_circle)
        }


        binding.icDownload.setOnClickListener {
            replaceFragment(DownloadFragment())
            binding.downloadBtn.setImageResource(R.drawable.arrow_down_circle2)
            binding.homeFilledBtn.setImageResource(R.drawable.home_filled)

        }



    }

//    override fun onBackPressed() {
//        if (currentFragment is DownloadFragment) {
//            replaceFragment(HomeFragment())
//        } else {
//            super.onBackPressed() // Allow default back button behavior
//        }
//    }




    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentReplace, fragment)
        transaction.commit()
    }



        private fun changeStatusBarColor(color1: String, color2: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window: Window = window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = android.graphics.Color.parseColor(color1)
                window.navigationBarColor = android.graphics.Color.parseColor(color2)
            }
        }

        private fun updateOrRequestPermissions(){
            val hasReadPermission = ContextCompat.checkSelfPermission(
                this,
                READ_EXTERNAL_STORAGE
            )== PackageManager.PERMISSION_GRANTED

            val hasWritePermission = ContextCompat.checkSelfPermission(
                this,
                WRITE_EXTERNAL_STORAGE
            )== PackageManager.PERMISSION_GRANTED

            val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

            readPermissionGranted = hasReadPermission
            writePermissionGranted = hasWritePermission || minSdk29

            val permissionToRequest = mutableListOf<String>()
            if(!writePermissionGranted){
                permissionToRequest.add(WRITE_EXTERNAL_STORAGE)
            }
            if(!readPermissionGranted){
                permissionToRequest.add(READ_EXTERNAL_STORAGE)
            }

            if(permissionToRequest.isNotEmpty()){
                permissionsLauncher.launch(permissionToRequest.toTypedArray())
            }
        }



}