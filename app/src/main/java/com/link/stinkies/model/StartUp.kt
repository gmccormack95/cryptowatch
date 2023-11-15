package com.link.stinkies.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.coincap.CoinCapRepo

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

        /*
        var msg = ""

        for (index in 1..330) {
            val imageNo = if(index < 10) {
                "00$index"
            } else if(index < 100) {
                "0${index}"
            } else {
                index
            }

            msg += "<item android:drawable=\"@drawable/ezgif_frame_${imageNo}\" android:duration=\"22\" />"
        }

        Log.d("ASDASD", msg)

         */
    }

    fun checkInitialised() {
        if(bizRepo?.loaded() == true && coinCapRepo?.loaded() == true) {
            loading.value = false
        }
    }

}