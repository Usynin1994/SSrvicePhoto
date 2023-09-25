package com.usunin1994.ssrvicephoto.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import com.usunin1994.ssrvicephoto.R
import com.usunin1994.ssrvicephoto.data.RepositoryImpl
import com.usunin1994.ssrvicephoto.databinding.ActivityMainBinding
import com.usunin1994.ssrvicephoto.domain.PhotoModel
import com.usunin1994.ssrvicephoto.ui.adapter.PhotoAdapter
import com.usunin1994.ssrvicephoto.util.toUri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity(), PhotoAdapter.OnLongClickListener {

    private val mainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var photoAdapter: PhotoAdapter? = null

    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val image = (data?.extras?.get("data") as? Bitmap)?.toUri(this)
            val list = photoAdapter?.photos?.toMutableList()
            list?.add(0, PhotoModel(image!!))
            photoAdapter?.photos = list!!
        } else {
            Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainBinding.root)

        mainBinding.button2.isEnabled = false

        val permissionWrite = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permissionCamera = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)

        if (permissionCamera == PackageManager.PERMISSION_GRANTED &&
            permissionWrite == PackageManager.PERMISSION_GRANTED) {
            mainBinding.button.isEnabled = true
        } else if (permissionCamera == PackageManager.PERMISSION_DENIED &&
            permissionWrite == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
            mainBinding.button.isEnabled = false
            mainBinding.button2.isEnabled = false
        }

        mainBinding.inputText.doAfterTextChanged {
            mainBinding.button2.isEnabled = !it.isNullOrEmpty()
        }

        photoAdapter = PhotoAdapter(this)
        with(mainBinding.photoRecycler){
            adapter = photoAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }

        mainBinding.button.setOnClickListener {
            startCamera()
        }

        mainBinding.button2.setOnClickListener {
            val rep = RepositoryImpl()
            rep.createFolder(mainBinding.inputText.text.toString())
        }
    }

    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePicture.launch(takePictureIntent)
    }

    override fun onLongClick(photo: PhotoModel) {
        val list = photoAdapter?.photos?.toMutableList()
        list?.remove(photo)
        photoAdapter?.photos = list!!
    }

    override fun onDestroy() {
        super.onDestroy()
        photoAdapter = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_CODE) {
            mainBinding.button.isEnabled = false
            mainBinding.button2.isEnabled = false
            return
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            mainBinding.button.isEnabled = true
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED &&
            grantResults[1] == PackageManager.PERMISSION_DENIED) {
            mainBinding.button.isEnabled = false
            mainBinding.button2.isEnabled = false
        }
    }

    companion object {
        const val SPAN_COUNT = 3
        const val REQUEST_CODE = 1
    }
}