package com.link.stinkies.model.coincap

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.composetest.model.api.Api
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.link.stinkies.model.StartUp

class TokenStatsResponse {

    var data: TokenStats? = null

    var timestamp: Long? = null

}

class TokenStats {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("rank")
    var rank: Int? = null

    @SerializedName("symbol")
    var symbol: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("supply")
    var supply: Float? = null

    @SerializedName("maxSupply")
    var maxSupply: Float? = null

    @SerializedName("marketCapUsd")
    var marketCapUsd: Float? = null

    @SerializedName("volumeUsd24Hr")
    var volumeUsd24Hr: Float? = null

    @SerializedName("priceUsd")
    var priceUsd: Float? = null

    @SerializedName("changePercent24Hr")
    var changePercent24Hr: Float? = null

    @SerializedName("vwap24Hr")
    var vwap24Hr: Float? = null

    var iconUrl: String? = null
        get() {
            return "${Api.icon}${symbol?.lowercase()}"
        }

    var chartData: MutableLiveData<TokenHistory> = MutableLiveData()

    fun getChartData() {
        val marketHistory = JsonObjectRequest(
            Request.Method.GET, Api.getHistory(id?: ""), null, { response ->
                Log.d("BizRepo", "Response: %s".format(response.toString()))
                chartData.value = Gson().fromJson(response.toString(), TokenHistory::class.java)
                chartData.value?.updateModel()
                StartUp.checkInitialised()
            }, { error ->
                Log.d("BizRepo", "Error: %s".format(error.toString()))
            }
        )

        CoinCapRepo.volleyManager?.addToRequestQueue(marketHistory)
    }

}