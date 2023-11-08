package com.link.stinkies.model.biz

import android.text.Html
import com.example.composetest.model.api.Api
import com.google.gson.annotations.SerializedName

class ThreadItem {

    @SerializedName("no")
    val id: Int? = null

    val name: String? = null

    val sub: String? = null

    @SerializedName("com")
    val comment: String? = null
        get() {
            field?.let {
                return Html.fromHtml(field).toString()
            }

            return field
        }

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

    /*

    val sticky: Boolean? = null

    val closed: Boolean? = null

    val now: String? = null

    val filename: String? = null

    val w: Int? = null

    val h: Int? = null

    @SerializedName("tn_w")
    val tnW: Int? = null

    @SerializedName("tn_h")
    val tnH: Int? = null

    val time: Long? = null

    val md5: String? = null

    val fsize: Int? = null

    val resto: Int? = null

    val capcode: String? = null

    @SerializedName("semantic_url")
    val semanticURL: String? = null

    val replies: Long? = null

    val images: Long? = null

    @SerializedName("omitted_posts")
    val omittedPosts: Long? = null

    @SerializedName("omitted_images")
    val omittedImages: Long? = null

    @SerializedName("last_modified")
    val lastModified: Long? = null

     */

}