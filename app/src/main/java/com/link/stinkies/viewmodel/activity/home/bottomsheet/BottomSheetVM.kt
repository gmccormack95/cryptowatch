package com.link.stinkies.viewmodel.activity.home.bottomsheet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.biz.Post

class BottomSheetVM : ViewModel() {

    var showSheet = mutableStateOf(false)
    var post: MutableLiveData<Post> = MutableLiveData<Post>()

    fun open(post: Post?) {
        showSheet.value = true
        this.post.value = post
    }

}