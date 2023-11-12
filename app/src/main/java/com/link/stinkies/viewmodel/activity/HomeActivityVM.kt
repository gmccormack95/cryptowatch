package com.link.stinkies.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.biz.Catalog
import com.link.stinkies.model.biz.ThreadResponse
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.model.coincap.TokenHistory

class HomeActivityVM : ViewModel() {

    var catalog: MutableLiveData<Catalog> = MutableLiveData()
    var thread: MutableLiveData<ThreadResponse> = MutableLiveData()
    var linkHourly: MutableLiveData<TokenHistory> = MutableLiveData()
    var catalogLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var threadLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var coinCapLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var bizRepo: BizRepo? = null
    private var coinCapRepo: CoinCapRepo? = null

    fun init(bizRepo: BizRepo, coinCapRepo: CoinCapRepo) {
        this.bizRepo = bizRepo
        this.coinCapRepo = coinCapRepo
        catalog = bizRepo.catalog
        thread = bizRepo.thread
        linkHourly = coinCapRepo.linkHourly
    }

    fun refreshCatalog() {
        if (catalogLoading.value == true) return

        catalogLoading.value = true
        bizRepo?.refreshCatalog {
            catalogLoading.value = false
        }
    }

    fun refreshThread(threadId: Int) {
        if (threadLoading.value == true) return

        thread.value = null
        threadLoading.value = true
        bizRepo?.refreshThread(threadId) {
            threadLoading.value = false
        }
    }

    fun refreshCurrentThread() {
        if (threadLoading.value == true) return

        threadLoading.value = true
        bizRepo?.refreshThread(thread.value?.threadId ?: -1) {
            threadLoading.value = false
        }
    }

    fun refreshCoinCap() {
        if (coinCapLoading.value == true) return

        coinCapLoading.value = true
        coinCapRepo?.refreshCoinCap {
            coinCapLoading.value = false
        }
    }

}