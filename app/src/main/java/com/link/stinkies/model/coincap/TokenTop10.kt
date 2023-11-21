package com.link.stinkies.model.coincap

class TokenTop10 {

    var data: ArrayList<TokenStats>? = arrayListOf()

    var timestamp: Long? = null

    fun getChartData() {
        data?.forEach { it.getChartData() }
    }

    fun loaded(): Boolean {
        data?.forEach {
            if(it.chartData.value == null) {
                return false
            }
        } ?: run {
            return false
        }

        return true
    }

}