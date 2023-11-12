package com.example.composetest.model.api

object Api {

    const val THREAD_ID = "{THREAD_ID}"

    const val bizEndpoint = "https://a.4cdn.org/biz"

    const val bizImage = "https://i.4cdn.org/biz"

    const val bizCatalog = "$bizEndpoint/catalog.json"

    const val bizThread = "$bizEndpoint/thread/$THREAD_ID.json"

    const val chainlinkHistory = "https://api.coincap.io/v2/assets/chainlink/history?interval=m1"

}