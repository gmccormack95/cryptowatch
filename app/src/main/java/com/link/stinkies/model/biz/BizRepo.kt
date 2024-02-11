package com.link.stinkies.model.biz

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.example.composetest.model.api.Api
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.link.stinkies.model.Constant
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.volley.VolleyManager


object BizRepo {

    var catalog: MutableLiveData<Catalog> = MutableLiveData()
    var thread: MutableLiveData<PostThread> = MutableLiveData()
    var volleyManager: VolleyManager? = null
    var threadPositions: HashMap<Int, Int> = HashMap()

    fun init(context: Context) {
        volleyManager = VolleyManager.getInstance(context)

        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, Api.bizCatalog, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                catalog.value = Catalog()
                catalog.value?.pages = Gson().fromJson(response.toString(), object : TypeToken<List<Page>>() {}.type)
                StartUp.checkInitialised()
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
            }
        )

        volleyManager?.addToRequestQueue(jsonObjectRequest)
    }

    fun refreshCatalog(onComplete: () -> Unit) {
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, Api.bizCatalog, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                catalog.value = Catalog()
                catalog.value?.pages = Gson().fromJson(response.toString(), object : TypeToken<List<Page>>() {}.type)
                onComplete()
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
                onComplete()
            }
        )

        volleyManager?.addToRequestQueue(jsonObjectRequest)
    }

    fun refreshThread(threadId: Int?, onComplete: () -> Unit) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, Api.bizThread.replace(Constant.THREAD_ID, threadId?.toString() ?: ""), null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                thread.value = Gson().fromJson(response.toString(), PostThread::class.java)
                thread.value?.threadId = threadId
                onComplete()
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
                onComplete()
            }
        )

        volleyManager?.addToRequestQueue(jsonObjectRequest)
    }

    fun loaded(): Boolean {
        return catalog.value != null
    }

}