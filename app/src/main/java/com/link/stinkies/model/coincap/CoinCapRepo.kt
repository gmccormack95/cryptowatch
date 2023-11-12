package com.link.stinkies.model.coincap

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.composetest.model.api.Api
import com.google.gson.Gson
import com.link.stinkies.model.volley.VolleyManager

object CoinCapRepo {

    var linkHourly: MutableLiveData<TokenHistory> = MutableLiveData()
    var volleyManager: VolleyManager? = null

    fun init(context: Context) {
        volleyManager = VolleyManager.getInstance(context)
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, Api.chainlinkHistory, null, { response ->
                        Log.d("BizRepo", "Response: %s".format(response.toString()))
                        linkHourly.value = Gson().fromJson(response.toString(), TokenHistory::class.java)
                    }, { error ->
                        Log.d("BizRepo", "Error: %s".format(error.toString()))
                    }
                )

                volleyManager?.addToRequestQueue(jsonObjectRequest)
                mainHandler.postDelayed(this, 30000)
            }
        })
    }

    fun refreshCoinCap(onComplete: () -> Unit) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, Api.chainlinkHistory, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                linkHourly.value = Gson().fromJson(response.toString(), TokenHistory::class.java)
                onComplete()
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
                onComplete()
            }
        )

        volleyManager?.addToRequestQueue(jsonObjectRequest)
    }

}