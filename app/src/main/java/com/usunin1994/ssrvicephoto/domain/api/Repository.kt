package com.usunin1994.ssrvicephoto.domain.api

import com.usunin1994.ssrvicephoto.domain.PhotoModel

interface Repository {
    fun createFolder(name: String)
    suspend fun saveContent(folderName: String, photos: MutableList<PhotoModel>)
}