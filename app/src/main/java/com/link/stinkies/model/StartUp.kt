package com.link.stinkies.model

import androidx.lifecycle.MutableLiveData
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.coincap.CoinCapRepo

object StartUp {

    var loading = MutableLiveData(true)
    var error = MutableLiveData(false)

    private var bizRepo: BizRepo? = null
    private var coinCapRepo: CoinCapRepo? = null

    fun init(bizRepo: BizRepo, coinCapRepo: CoinCapRepo) {
        this.bizRepo = bizRepo
        this.coinCapRepo = coinCapRepo
    }

    fun checkInitialised() {
        if(bizRepo?.loaded() == true && coinCapRepo?.loaded() == true) {
            loading.value = false
        }
    }

}