package com.link.stinkies.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.biz.Catalog

class HomeActivityVM : ViewModel() {

    var catalog: MutableLiveData<Catalog> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var bizRepo: BizRepo? = null

    fun init(bizRepo: BizRepo) {
        this.bizRepo = bizRepo
        catalog = bizRepo.catalog
    }

    fun refresh() {
        loading.value = true
        bizRepo?.refresh {
            loading.value = false
        }
    }

}