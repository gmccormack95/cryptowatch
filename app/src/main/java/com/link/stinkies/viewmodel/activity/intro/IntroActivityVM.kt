package com.link.stinkies.viewmodel.activity.intro

import android.content.Context
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.coincap.CoinCapRepo

class IntroActivityVM : ViewModel() {

    fun init(context: Context, bizRepo: BizRepo, coinCapRepo: CoinCapRepo) {
        StartUp.init(context, BizRepo, CoinCapRepo)
    }

}