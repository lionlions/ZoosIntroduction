package com.cindy.zoosintroduction.model

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.cindy.zoosintroduction.R

data class ZoosModel(
    var data: ZoosIntroduction? = null
)

data class ZoosIntroduction(
    var zoos_list: List<Zoo>? = null
)

data class Zoo(
    var E_no: Int = -1,
    var E_Category: String? = null,
    var E_Name: String? = null,
    var E_Pic_URL: String? = null,
    var E_Info: String? = null,
    var E_Memo: String? = null,
    var E_Geo: String? = null,
    var E_URL: String? = null
){
    companion object {

        private val TAG: String = javaClass.simpleName

        @JvmStatic
        @BindingAdapter("zooImage")
        fun loadImage(imageView: ImageView, url: String) {
            Log.v(TAG,"url: $url")
            var imageUrl = url
            if(imageUrl.contains("http://")){
                imageUrl = imageUrl.replace("http", "https")
            }
            Glide.with(imageView.context)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .fitCenter()
                        .format(DecodeFormat.PREFER_RGB_565)
                )
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade(500))
                .into(imageView)
        }
    }
}