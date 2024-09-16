package com.softapp.thewallpapertimes534965647

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.softapp.thewallpapertimes534965647.databinding.ActivityFinalWallpaperBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.URL
import java.util.Random

class FinalWallpaper : AppCompatActivity() {

    lateinit var binding: ActivityFinalWallpaperBinding

    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFinalWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        changeStatusBarColor("#000000","#000000")

        loadInterAd()



        val url = intent.getStringExtra("link")
        val urlImage = URL(url)

        binding.backButtonWal.setOnClickListener{
            finish()
        }


        Glide.with(this).load(url).into(binding.finalWallpaper)

        binding.btnSetWallpaper.setOnClickListener {

            showInterAd()

            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }

            GlobalScope.launch(Dispatchers.Main) {
                val wallpaperManager = WallpaperManager.getInstance(applicationContext)
                wallpaperManager.setBitmap(result.await())
//                wallpaperManager.setBitmap(result.await(),null,true,WallpaperManager.FLAG_LOCK)


            }
            Toast.makeText(this, "Image applied", Toast.LENGTH_SHORT).show()
        }




        val random1 = Random().nextInt(528985)
        val random2 = Random().nextInt(952663)
        val filename = "AMOLED - ${random1 + random2}"
        binding.btnDownload.setOnClickListener {
            showInterAd()
            val result: Deferred<Bitmap?> = GlobalScope.async {
                urlImage.toBitmap()
            }
            GlobalScope.launch(Dispatchers.Main) {
                result.await()?.let { it1 -> saveImage(it1) }
                result.await()?.let { it1 -> savePhotoToExternalStorage(filename, it1) }
            }

            Toast.makeText(this, "Image Downloaded", Toast.LENGTH_SHORT).show()
        }
    }




    fun URL.toBitmap(): Bitmap? {
        return try {
            BitmapFactory.decodeStream(openStream())
        } catch (e: IOException) {
            null
        }
    }




//    private fun saveImage(image: Bitmap?) {
//        val random1 = Random().nextInt(528985)
//        val random2 = Random().nextInt(952663)
//        val name = "AMOLED - ${random1 + random2}"
//        val data: OutputStream
//        try {
//            val resolver = contentResolver
//            val contentValues = ContentValues()
//            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
//            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
//            contentValues.put(
//                MediaStore.MediaColumns.RELATIVE_PATH,
//                Environment.DIRECTORY_PICTURES + File.separator + "Amoled Wallpaper"
//            )
//            val imageUri =
//                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
//            data = resolver.openOutputStream(Objects.requireNonNull(imageUri)!!)!!
//            image?.compress(Bitmap.CompressFormat.JPEG, 100, data)
//            Objects.requireNonNull<OutputStream?>(data)
//            Toast.makeText(this, "Image Downloaded", Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
//            Log.e("SaveImage", "Error saving image: ${e.message}")
//            Toast.makeText(this, "Image Cannot be Download", Toast.LENGTH_SHORT).show()
//        }
//    }


    //This is new code
    private fun saveImage( bmp:Bitmap):Boolean{
        val random1 = Random().nextInt(528985)
        val random2 = Random().nextInt(952663)
        val filename = "AMOLED - ${random1 + random2}"
        return try{
            openFileOutput("$filename.jpg", MODE_PRIVATE).use{stream->
                if(!bmp.compress(Bitmap.CompressFormat.JPEG , 95 , stream)){
                    throw IOException("Couldn't save bitmap")
                }

            }
            true
        }catch(e:IOException){
            e.printStackTrace()
            false
        }
    }

    private fun changeStatusBarColor(color1: String, color2: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = android.graphics.Color.parseColor(color1)
            window.navigationBarColor = android.graphics.Color.parseColor(color2)
        }
    }

    fun  savePhotoToExternalStorage(displayName:String, bmp: Bitmap):Boolean{
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        }?:MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues  = ContentValues().apply{
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
            put(MediaStore.Images.Media.WIDTH, bmp.width)
            put(MediaStore.Images.Media.HEIGHT, bmp.height)
        }

        return try{
            val contentResolver = contentResolver

            contentResolver.insert(imageCollection, contentValues)?.also{ uri ->
                contentResolver.openOutputStream(uri).use{ outputStream->
                    if(!outputStream?.let { bmp.compress(Bitmap.CompressFormat.JPEG,95, it) }!!){
                        throw IOException("Couldnot save bitmap")
                    }

                }

            } ?: throw IOException("Couldnot  create mediaStore entry")
            true
        }catch (e:IOException ){
            e.printStackTrace()
            false
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadInterAd() {
        var adRequest = AdRequest.Builder().build()


            InterstitialAd.load(this,"ca-app-pub-3823120616991641/9072912641", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {

                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {

                    mInterstitialAd = interstitialAd
                }
            })

    }

    private fun showInterAd(){
        if(mInterstitialAd != null){
            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback(){
                override fun onAdClicked() {
                    super.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
//                    Toast.makeText(baseContext, "Yes it works ",Toast.LENGTH_SHORT).show()
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                }

            }
             mInterstitialAd?.show(this)
        }else{
//            Toast.makeText(baseContext, "NO  works ",Toast.LENGTH_SHORT).show()
        }
    }



}