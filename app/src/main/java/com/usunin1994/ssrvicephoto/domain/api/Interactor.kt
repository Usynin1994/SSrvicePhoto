package com.usunin1994.ssrvicephoto.domain.api

import com.usunin1994.ssrvicephoto.domain.PhotoModel

interface Interactor {
    fun createFolder(name: String)
    suspend fun saveContent(photos: MutableList<PhotoModel>)
}