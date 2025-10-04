package com.example.taxisharing.utils

import android.content.ContentResolver
import android.content.Context

// FileUtil is used to assist in file operations, such as reaching the local files
class FileUtil {

    // readTextFromAsset is used to get data from the asset folder
    fun readTextFromAsset(filename: String, context: Context): String {
        return try{
            context.resources.assets.open(filename)
                .bufferedReader()
                .use { it.readText() }
        }catch (e: Exception) {
            e.message!!
        }
    }

}