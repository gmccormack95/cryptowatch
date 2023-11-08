package com.link.stinkies.model.biz

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.composetest.model.api.Api
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.link.stinkies.model.volley.VolleyManager


object BizRepo {

    var catalog: MutableLiveData<Catalog> = MutableLiveData()

    fun init(context: Context) {
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, Api.bizCatalog, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                catalog.value = Catalog()
                catalog.value?.pages = Gson().fromJson(response.toString(), object : TypeToken<List<Page>>() {}.type)
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
            }
        )

        VolleyManager.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }

}