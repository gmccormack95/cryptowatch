package com.link.stinkies.viewmodel.activity.home.bottomsheet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class BottomSheetVM : ViewModel() {

    var showSheet = mutableStateOf(false)
    var postId: Int? = null

    fun open(postId: Int) {
        showSheet.value = true
        this.postId = postId
    }

}