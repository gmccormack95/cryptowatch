package com.link.stinkies.viewmodel.activity.home.thread

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.link.stinkies.model.biz.BizRepo
import com.link.stinkies.model.biz.PostThread
import com.link.stinkies.viewmodel.activity.home.replies.RepliesDrawerVM

class ThreadLayoutVM : ViewModel() {

    val repliesDrawerVM = RepliesDrawerVM()

    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
    var thread: MutableLiveData<PostThread> = MutableLiveData()
    var scrollPositions = hashMapOf<Int, Int>()

    private var bizRepo: BizRepo? = null

    fun init(bizRepo: BizRepo) {
        this.bizRepo = bizRepo
        thread = bizRepo.thread
        repliesDrawerVM.init()
    }

    fun refreshThread(threadId: Int) {
        if (loading.value == true) return

        thread.value = null
        loading.value = true
        bizRepo?.refreshThread(threadId) {
            loading.value = false
        }
    }

    fun refreshCurrentThread() {
        if (loading.value == true) return

        loading.value = true
        bizRepo?.refreshThread(thread.value?.threadId) {
            loading.value = false
        }
    }

    fun getReplies(postId: Int?) {
        repliesDrawerVM.replies.value = thread.value?.getReplies(postId)
    }

}