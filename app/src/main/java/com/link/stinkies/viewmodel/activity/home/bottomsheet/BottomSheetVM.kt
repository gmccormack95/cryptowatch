package com.link.stinkies.viewmodel.activity.home.bottomsheet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.biz.Post
import com.link.stinkies.model.biz.PostThread

class BottomSheetVM : ViewModel() {

    var showSheet = mutableStateOf(false)
    var thread: MutableLiveData<PostThread> = MutableLiveData<PostThread>()
    var post: MutableLiveData<Post> = MutableLiveData<Post>()

    fun open(thread: PostThread?, post: Post?) {
        showSheet.value = true
        this.thread.value = thread
        this.post.value = post
    }

}