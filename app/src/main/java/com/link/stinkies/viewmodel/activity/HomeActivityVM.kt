package com.link.stinkies.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.biz.Catalog
import com.link.stinkies.model.biz.ThreadResponse
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.viewmodel.activity.charts.ChartLayoutVM

class HomeActivityVM : ViewModel() {

    var chartLayoutVM = ChartLayoutVM()

    var catalog: MutableLiveData<Catalog> = MutableLiveData()
    var thread: MutableLiveData<ThreadResponse> = MutableLiveData()
    var catalogLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    var threadLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var bizRepo: BizRepo? = null

    fun init(bizRepo: BizRepo, coinCapRepo: CoinCapRepo) {
        StartUp.init(bizRepo, coinCapRepo)

        this.bizRepo = bizRepo
        this.chartLayoutVM.init(coinCapRepo)

        catalog = bizRepo.catalog
        thread = bizRepo.thread
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

}