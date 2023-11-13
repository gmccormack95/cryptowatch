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
import com.link.stinkies.model.volley.VolleyManager

object CoinCapRepo {

    var interval: MutableLiveData<Interval> = MutableLiveData(Interval.Day1)
    var chartData: MutableLiveData<TokenHistory> = MutableLiveData()
    var chainlink: MutableLiveData<TokenStats> = MutableLiveData()
    var top10: MutableLiveData<TokenTop10> = MutableLiveData()
    var volleyManager: VolleyManager? = null

    fun init(context: Context) {
        volleyManager = VolleyManager.getInstance(context)
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                refreshCoinCap { }
                mainHandler.postDelayed(this, 30000)
            }
        })
    }

    fun refreshCoinCap(onComplete: () -> Unit) {
        var marketHistoryResponded = false
        var chainlinkStatsResponded = false
        var top10Responded = false

        val marketHistory = JsonObjectRequest(
            Request.Method.GET, Api.chainlinkHistory.replace(Api.INTERVAL, interval.value?.coinCap ?: "m1"), null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                chartData.value = Gson().fromJson(response.toString(), TokenHistory::class.java)
                marketHistoryResponded = true
                if (marketHistoryResponded && chainlinkStatsResponded && top10Responded) {
                    onComplete()
                }
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
                marketHistoryResponded = true
                if (marketHistoryResponded && chainlinkStatsResponded && top10Responded) {
                    onComplete()
                }
            }
        )

        val chainlinkCurrent = JsonObjectRequest(
            Request.Method.GET, Api.chainlinkStats, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                chainlink.value = Gson().fromJson(response.toString(), TokenStatsResponse::class.java).data
                chainlinkStatsResponded = true
                if (marketHistoryResponded && chainlinkStatsResponded && top10Responded) {
                    onComplete()
                }
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
                chainlinkStatsResponded = true
                if (marketHistoryResponded && chainlinkStatsResponded && top10Responded) {
                    onComplete()
                }
            }
        )

        val top10 = JsonObjectRequest(
            Request.Method.GET, Api.top10, null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                top10.value = Gson().fromJson(response.toString(), TokenTop10::class.java)
                top10Responded = true
                if (marketHistoryResponded && chainlinkStatsResponded && top10Responded) {
                    onComplete()
                }
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
                top10Responded = true
                if (marketHistoryResponded && chainlinkStatsResponded && top10Responded) {
                    onComplete()
                }
            }
        )

        volleyManager?.addToRequestQueue(marketHistory)
        volleyManager?.addToRequestQueue(chainlinkCurrent)
        volleyManager?.addToRequestQueue(top10)
    }

}