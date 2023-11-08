package com.link.stinkies.viewmodel.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.biz.Catalog

class HomeActivityVM : ViewModel() {

    var catalog: MutableLiveData<Catalog> = MutableLiveData()

    fun init(bizRepo: BizRepo) {
        catalog = bizRepo.catalog
    }

}