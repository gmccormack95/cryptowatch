package com.link.stinkies.model

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.ContextCompat.getSystemService
import androidx.media3.database.StandaloneDatabaseProvider
import com.link.stinkies.model.biz.Post
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


object ImageDownloadManager {

    fun get(post: Post?) {
        /*
        // Note: This should be a singleton in your app.
        val databaseProvider = StandaloneDatabaseProvider(context)

        // A download cache should not evict media, so should use a NoopCacheEvictor.
        val downloadCache = SimpleCache(downloadDirectory, NoOpCacheEvictor(), databaseProvider)

        // Create a factory for reading the data from the network.
        val dataSourceFactory = DefaultHttpDataSource.Factory()

        // Choose an executor for downloading data. Using Runnable::run will cause each download task to
        // download data on its own thread. Passing an executor that uses multiple threads will speed up
        // download tasks that can be split into smaller parts for parallel execution. Applications that
        // already have an executor for background downloads may wish to reuse their existing executor.
        val downloadExecutor = Executor(Runnable::run)

        // Create the download manager.
        val downloadManager =
                    DownloadManager(context, databaseProvider, downloadCache, dataSourceFactory, downloadExecutor)

        // Optionally, properties can be assigned to configure the download manager.
        downloadManager.requirements = requirements
        downloadManager.maxParallelDownloads = 3
        */
    }

    /*
    private fun load(string: String): Bitmap? {
        val url = stringToURL(string)
        val connection: HttpURLConnection?
        try {
            connection = url?.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)
            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    private fun stringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
    }

    private fun saveMediaToStorage(bitmap: Bitmap?) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

     */

}