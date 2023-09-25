package com.usunin1994.ssrvicephoto.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun Bitmap.toUri(context: Context): Uri?   {
    var uri: Uri? = null
    try {
        // Создаем временный файл во внешнем каталоге файлов
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "image_${System.currentTimeMillis()}")
        // Сохраняем Bitmap в файл
        val outputStream = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        // Получаем Uri для файла
        uri = FileProvider.getUriForFile(context, "com.sservice.fileprovider", file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return uri
}