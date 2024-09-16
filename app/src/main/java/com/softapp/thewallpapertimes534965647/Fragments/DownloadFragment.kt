package com.softapp.thewallpapertimes534965647.Fragments

import InternalStoragePhoto
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager

import com.softapp.thewallpapertimes534965647.Adapter.DownloadAdapter
import com.softapp.thewallpapertimes534965647.databinding.FragmentDownloadBinding
import com.softapp.thewallpapertimes534965647.sdk29AndUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException


class DownloadFragment : Fragment() {

    lateinit var binding: FragmentDownloadBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentDownloadBinding.inflate(layoutInflater, container, false)

        //New Code

        lifecycleScope.launch { // Launch a coroutine
            loadPhotosFromInternalStorageIntoRecyclerView()
        }

//        loadPhotosFromInternalStorageIntoRecyclerView()

        lifecycleScope.launch {

        val photo = loadPhotosFromInternalStorage()
            binding.rcvColection.adapter = DownloadAdapter(requireContext(), photo)
        }

//        val deleteButton = findViewById<ImageButton>(R.id.deleteButton)






        binding.rcvColection.layoutManager= GridLayoutManager (requireContext(),  2)








//        //OLd code
//
//        val allFiles: Array<File>
//        val imageList = arrayListOf<String>()
//
//        val targetPath =
//            Environment.getExternalStorageDirectory().absolutePath + "/Pictures/Amoled Wallpaper"
//
//        val targetFile = File(targetPath)
//        allFiles = targetFile.listFiles()!!
//
//        for (data in allFiles) {
//            imageList.add(data.absolutePath)
//        }
////
////        binding.rcvColection.layoutManager=
////            StaggeredGridLayoutManager (  2, StaggeredGridLayoutManager.VERTICAL)
////        binding.rcvColection.adapter= CollectionAdapter(requireContext(), imageList)
//
//
//
//        binding.rcvColection.layoutManager= GridLayoutManager (requireContext(),  2)
//        binding.rcvColection.adapter = CollectionAdapter(requireContext(), imageList)


        return binding.root

    }




    suspend fun loadPhotosFromInternalStorageIntoRecyclerView(){
        lifecycleScope.launch {
            val photos = loadPhotosFromInternalStorage()
            val imageList = arrayListOf<String>()
//            CollectionAdapter.imageList(photos)
        }
    }



    suspend fun loadPhotosFromInternalStorage():List<InternalStoragePhoto>{
        return withContext(Dispatchers.IO){
//                val files = requireContext().filesDir?.listFiles() ?: emptyList()

            val files = requireContext().filesDir?.listFiles()
            files?.filter{ it.canRead() && it.isFile && it.name.endsWith(".jpg")}?.map{
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                InternalStoragePhoto(it.name,bmp)
            }?:listOf()
        }
    }

    private fun deletePhotoFromInternalStorage(filename : String): Boolean{
        val file = File(context?.filesDir, filename)
        return try {
            file.delete()
        } catch (e:Exception){
            e.printStackTrace()
            false
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
            val contentResolver = requireActivity().contentResolver

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


}