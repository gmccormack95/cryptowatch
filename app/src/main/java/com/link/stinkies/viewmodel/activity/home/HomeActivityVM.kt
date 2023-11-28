package com.link.stinkies.viewmodel.activity

import android.content.Context
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.StartUp
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.biz.Catalog
import com.link.stinkies.model.biz.ThreadResponse
import com.link.stinkies.model.coincap.CoinCapRepo
import com.link.stinkies.viewmodel.activity.charts.ChartLayoutVM
import com.link.stinkies.viewmodel.activity.home.bottomsheet.BottomSheetVM
import com.link.stinkies.viewmodel.activity.home.thread.ThreadLayoutVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class HomeActivityVM : ViewModel() {

    var chartLayoutVM = ChartLayoutVM()
    var threadLayoutVM = ThreadLayoutVM()
    var bottomsheetVM = BottomSheetVM()

    val drawerState = DrawerState(initialValue = DrawerValue.Closed)

    var catalog: MutableLiveData<Catalog> = MutableLiveData()
    var catalogLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var bizRepo: BizRepo? = null

    fun init(context: Context, bizRepo: BizRepo, coinCapRepo: CoinCapRepo) {
        StartUp.init(context, BizRepo, CoinCapRepo)

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

    fun openReplies(scope: CoroutineScope, postId: Int) {
        scope.launch {
            threadLayoutVM.repliesDrawerVM.listState.scrollToItem(0)
            threadLayoutVM.getReplies(postId)
            drawerState.open()
        }
    }

}