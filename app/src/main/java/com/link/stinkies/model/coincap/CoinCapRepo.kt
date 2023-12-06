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
import com.link.stinkies.layout.activity.home.charts.vico.Interval
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.volley.VolleyManager

object CoinCapRepo {

    var interval: MutableLiveData<Interval> = MutableLiveData(Interval.Day1)
    var chainlinkData: MutableLiveData<TokenHistory> = MutableLiveData()
    var chainlink: MutableLiveData<TokenStats> = MutableLiveData()
    var top10: MutableLiveData<TokenTop10> = MutableLiveData()
    var volleyManager: VolleyManager? = null

    fun init(context: Context) {
        volleyManager = VolleyManager.getInstance(context)
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                refreshCoinCap {
                    StartUp.checkInitialised()
                }
                mainHandler.postDelayed(this, 30000)
            }
        })

        val top10 = JsonObjectRequest(
            Request.Method.GET, Api.top10, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                top10.value = Gson().fromJson(response.toString(), TokenTop10::class.java)
                top10.value?.getChartData()
                StartUp.checkInitialised()
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
            }
        )

        volleyManager?.addToRequestQueue(top10)
    }

    fun refreshCoinCap(onComplete: () -> Unit) {
        var marketHistoryResponded = false
        var chainlinkStatsResponded = false

        val marketHistory = JsonObjectRequest(
            Request.Method.GET, Api.getChainlinkHistory(interval.value), null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                chainlinkData.value = Gson().fromJson(response.toString(), TokenHistory::class.java)
                chainlinkData.value?.updateModel()
                marketHistoryResponded = true
                if (marketHistoryResponded && chainlinkStatsResponded) {
                    onComplete()
                }
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
                marketHistoryResponded = true
                if (marketHistoryResponded && chainlinkStatsResponded) {
                    onComplete()
                }
            }
        )

        val chainlinkCurrent = JsonObjectRequest(
            Request.Method.GET, Api.chainlinkStats, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                chainlink.value = Gson().fromJson(response.toString(), TokenStatsResponse::class.java).data
                chainlinkStatsResponded = true
                if (marketHistoryResponded && chainlinkStatsResponded) {
                    onComplete()
                }
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
                chainlinkStatsResponded = true
                if (marketHistoryResponded && chainlinkStatsResponded) {
                    onComplete()
                }
            }
        )

        val top10 = JsonObjectRequest(
            Request.Method.GET, Api.top10, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                top10.value = Gson().fromJson(response.toString(), TokenTop10::class.java)
                top10.value?.getChartData()
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
            }
        )

        volleyManager?.addToRequestQueue(marketHistory)
        volleyManager?.addToRequestQueue(chainlinkCurrent)
        volleyManager?.addToRequestQueue(top10)
    }

    fun loaded(): Boolean {
        return chainlinkData.value != null && chainlink.value != null && top10.value?.loaded() == true
    }

}