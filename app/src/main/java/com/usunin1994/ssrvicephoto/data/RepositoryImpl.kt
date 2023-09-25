package com.usunin1994.ssrvicephoto.data

import android.os.Environment
import com.usunin1994.ssrvicephoto.domain.PhotoModel
import com.usunin1994.ssrvicephoto.domain.api.Repository
import java.io.File

class RepositoryImpl: Repository {
    override fun createFolder(name: String) {
        val folder = File(Environment.getExternalStorageDirectory().toString() + "/" + name)
        if (!folder.exists()) {
            folder.mkdirs()
        } else {
            return
        }
    }

    override suspend fun saveContent(folderName: String, photos: MutableList<PhotoModel>) {
        TODO("Not yet implemented")
    }
}
