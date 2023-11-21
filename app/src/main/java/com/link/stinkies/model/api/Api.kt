package com.example.composetest.model.api

object Api {

    const val THREAD_ID = "{THREAD_ID}"

    const val INTERVAL = "{INTERVAL}"

    const val ASSET = "{ASSET}"

    const val START_TIME = "{START_TIME}"

    const val END_TIME = "{END_TIME}"

    const val bizEndpoint = "https://a.4cdn.org/biz"

    const val bizImage = "https://i.4cdn.org/biz"

    const val bizCatalog = "$bizEndpoint/catalog.json"

    const val bizThread = "$bizEndpoint/thread/$THREAD_ID.json"

    const val chainlinkHistory = "https://api.coincap.io/v2/assets/chainlink/history?interval=$INTERVAL"

    const val chainlinkStats = "https://api.coincap.io/v2/assets/chainlink"

    const val top10 = "https://api.coincap.io/v2/assets?limit=10"

    const val icon = "https://coinicons-api.vercel.app/api/icon/"

    fun getHistory(asset: String): String {
        var end = System.currentTimeMillis()
        var start = end - (24 * 60 * 60 * 1000)
        return "https://api.coincap.io/v2/assets/$asset/history?interval=h1&start=$start&end=$end"
    }

}