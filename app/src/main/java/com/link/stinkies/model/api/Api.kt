package com.example.composetest.model.api

import com.link.stinkies.layout.activity.home.charts.vico.Interval
import com.link.stinkies.model.Constant.INTERVAL
import com.link.stinkies.model.Constant.THREAD_ID

object Api {

    const val bizEndpoint = "https://a.4cdn.org/biz"

    const val bizImage = "https://i.4cdn.org/biz"

    const val bizCatalog = "$bizEndpoint/catalog.json"

    const val bizThread = "$bizEndpoint/thread/$THREAD_ID.json"

    const val chainlinkHistory = "https://api.coincap.io/v2/assets/chainlink/history?interval=$INTERVAL"

    const val chainlinkStats = "https://api.coincap.io/v2/assets/chainlink"

    const val top10 = "https://api.coincap.io/v2/assets?limit=10"

    const val icon = "https://coinicons-api.vercel.app/api/icon/"

    fun getChainlinkHistory(interval: Interval?): String {
        interval?.let {
            val end = System.currentTimeMillis()
            val start = end - interval.milliseconds
            return "https://api.coincap.io/v2/assets/chainlink/history?interval=${interval.coinCapValue}&start=$start&end=$end"
        }

        return ""
    }

    fun getHistory(asset: String): String {
        var end = System.currentTimeMillis()
        var start = end - (24 * 60 * 60 * 1000)
        return "https://api.coincap.io/v2/assets/$asset/history?interval=h1&start=$start&end=$end"
    }

}