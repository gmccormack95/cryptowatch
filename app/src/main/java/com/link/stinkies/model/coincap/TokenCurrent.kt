package com.link.stinkies.model.coincap

import com.google.gson.annotations.SerializedName

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

}