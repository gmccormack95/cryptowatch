package com.link.stinkies.model.biz

import android.text.Html
import androidx.compose.ui.Modifier
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.GlideImage
import com.example.composetest.model.api.Api
import com.google.gson.annotations.SerializedName

class Post {

    @SerializedName("no")
    val id: Int? = null

    @SerializedName("id")
    val userId: String? = null

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

    var expanded = false

}