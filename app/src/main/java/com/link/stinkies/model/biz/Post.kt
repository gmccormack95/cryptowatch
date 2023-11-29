package com.link.stinkies.model.biz

import android.text.Html
import android.text.Spanned
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.lifecycle.MutableLiveData
import com.example.composetest.model.api.Api
import com.google.gson.annotations.SerializedName

class Post {

    @SerializedName("no")
    val id: Int? = null

    @SerializedName("id")
    val userId: String? = null

    val name: String? = null

    val sub: String? = null
        get() {
            field?.let {
                return Html.fromHtml(field).toString()
            }

            return field
        }

    val comment: String? = null
        get() {
            rawComment?.let {
                return Html.fromHtml(rawComment).toString()
            }

            return field
        }

    val spannedComment: Spanned? = null
        get() {
            rawComment?.let {
                val greenText = it
                    .replace("span class=\"quote\"", "font color=#789922")
                    .replace(";\"", "'")
                    .replace("</span>", "</font>")


                return HtmlCompat.fromHtml(greenText, HtmlCompat.FROM_HTML_MODE_COMPACT)
            }

            return field
        }

    @SerializedName("com")
    private val rawComment: String? = null

    val thumbnailUrl: String?
        get() {
            if(tim != null && ext != null) {
                return "${Api.bizImage}/${tim}s$ext"
            }

            return null
        }

    val imageUrl: String?
        get() {
            if(tim != null && ext != null) {
                return "${Api.bizImage}/$tim$ext"
            }

            return null
        }

    private val ext: String? = null

    private val tim: Long? = null

    val stickied: Boolean
        get() {
            return sticky == 1
        }

    val locked: Boolean
        get() {
            return closed == 1
        }

    private val sticky: Int? = null

    private val closed: Int? = null

    var now: String? = null

    var responses: ArrayList<Int>? = null
        get() {
            val comment = rawComment
            val list = arrayListOf<Int>()

            comment?.let {
                while (comment.indexOf("<a href=\"#p") != -1) {
                    val startIndex = comment.indexOf("<a href=\"#p") + 11
                    val endIndex = startIndex + 7

                    comment.substring(startIndex, endIndex).toIntOrNull()?.let {
                        list.add(it)
                    }

                    comment.removeRange(0, endIndex)
                }
            }

            return list
        }

    var expanded = MutableLiveData(false)

    var replies: Int? = null

    fun hasReplied(postId: Int?): Boolean {
        return rawComment?.contains("<a href=\"#p$postId\" class=\"quotelink\">") == true
    }


}