package com.link.stinkies.viewmodel.activity.charts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.layout.activity.home.charts.vico.Interval
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.model.coincap.TokenHistory
import com.link.stinkies.model.coincap.TokenStats
import com.link.stinkies.model.coincap.TokenTop10

class ChartLayoutVM : ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var chainlinkData: MutableLiveData<TokenHistory> = MutableLiveData()
    var chainlink: MutableLiveData<TokenStats> = MutableLiveData()
    var top10: MutableLiveData<TokenTop10> = MutableLiveData()
    var interval: MutableLiveData<Interval> = MutableLiveData()

    private var coinCapRepo: CoinCapRepo? = null

    fun init(coinCapRepo: CoinCapRepo) {
        this.coinCapRepo = coinCapRepo
        chainlinkData = coinCapRepo.chainlinkData
        chainlink = coinCapRepo.chainlink
        top10 = coinCapRepo.top10
        interval = coinCapRepo.interval
    }

    fun refreshCoinCap() {
        if (loading.value == true) return

        loading.value = true
        coinCapRepo?.refreshCoinCap {
            loading.value = false
        }
    }

}