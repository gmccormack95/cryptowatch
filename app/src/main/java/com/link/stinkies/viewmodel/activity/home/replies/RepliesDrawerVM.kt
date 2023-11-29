package com.link.stinkies.viewmodel.activity.home.replies

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.biz.Post

class RepliesDrawerVM : ViewModel() {

    var replies: MutableLiveData<ArrayList<Post>> = MutableLiveData()

    fun init() {

    }

}