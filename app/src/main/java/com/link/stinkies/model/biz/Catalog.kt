package com.link.stinkies.model.biz

class Catalog {

    var pages: ArrayList<Page> = arrayListOf()

    val threads: ArrayList<ThreadItem>
        get() {
            val threads = arrayListOf<ThreadItem>()
            for (page in pages) {
                threads.addAll(page.threads)
            }
            return threads
        }

}