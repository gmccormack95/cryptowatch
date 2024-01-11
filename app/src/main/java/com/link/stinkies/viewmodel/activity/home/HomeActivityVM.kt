package com.link.stinkies.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.biz.Catalog
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.viewmodel.activity.charts.ChartLayoutVM
import com.link.stinkies.viewmodel.activity.home.bottomsheet.BottomSheetVM
import com.link.stinkies.viewmodel.activity.home.thread.ThreadLayoutVM

class HomeActivityVM : ViewModel() {

    var chartLayoutVM = ChartLayoutVM()
    var threadLayoutVM = ThreadLayoutVM()
    var bottomsheetVM = BottomSheetVM()

    var catalog: MutableLiveData<Catalog> = MutableLiveData()
    var catalogLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var bizRepo: BizRepo? = null

    fun init(bizRepo: BizRepo, coinCapRepo: CoinCapRepo) {
        this.bizRepo = bizRepo
        this.chartLayoutVM.init(coinCapRepo)
        this.threadLayoutVM.init(BizRepo)

        catalog = bizRepo.catalog
    }

    fun refreshCatalog() {
        if (catalogLoading.value == true) return

        catalogLoading.value = true
        bizRepo?.refreshCatalog {
            catalogLoading.value = false
        }
    }

    fun refreshThread(threadId: Int) {
        threadLayoutVM.refreshThread(threadId)
    }

    fun refreshCurrentThread() {
        threadLayoutVM.refreshCurrentThread()
    }

    fun openReplies(postId: Int?) {
        threadLayoutVM.getReplies(postId)
    }

}