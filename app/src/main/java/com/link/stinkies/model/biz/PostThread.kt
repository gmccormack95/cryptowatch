package com.link.stinkies.model.biz

class PostThread {

    var threadId: Int? = null

    var posts: ArrayList<Post> = arrayListOf()

    fun getReplies(postId: Int?): ArrayList<Post> {
        val chain: ArrayList<Post> = arrayListOf()

        posts.firstOrNull{ it.id == postId }?.let {
            chain.add(it)
        }

        for (post in posts) {
            if (post.hasReplied(postId)) {
                chain.add(post)
            }
        }

        return chain
    }

}