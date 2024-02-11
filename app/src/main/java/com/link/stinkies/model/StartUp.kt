package com.link.stinkies.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.model.web3.Web3Manager

object StartUp {

    var loading = MutableLiveData(true)
    var error = MutableLiveData(false)

    private var bizRepo: BizRepo? = null
    private var coinCapRepo: CoinCapRepo? = null

    fun init(context: Context, bizRepo: BizRepo, coinCapRepo: CoinCapRepo) {
        this.bizRepo = bizRepo
        this.coinCapRepo = coinCapRepo
        BizRepo.init(context)
        CoinCapRepo.init(context)
        Web3Manager.init(context)
    }

    fun checkInitialised() {
        if(bizRepo?.loaded() == true && coinCapRepo?.loaded() == true) {
            loading.value = false
        }
    }

}