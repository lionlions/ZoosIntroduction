package com.cindy.zoosintroduction.model

import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlantsInfoModel(
    var result: PlantsListResult? = PlantsListResult()
): Parcelable

@Parcelize
data class PlantsListResult(
    var count: Int = 0,
    var limit: Int = 0,
    var offset: Int = 0,
    var results: List<PlantInfo>? = listOf(),
    var sort: String? = ""
): Parcelable

@Parcelize
data class PlantInfo(
    var F_AlsoKnown: String? = "",
    var F_Brief: String? = "",
    var F_CID: String? = "",
    var F_Code: String? = "",
    var F_Family: String? = "",
    var F_Feature: String? = "",
    var F_Function_Application: String? = "",
    var F_Genus: String? = "",
    var F_Geo: String? = "",
    var F_Keywords: String? = "",
    var F_Location: String? = "",
    var F_Name_En: String? = "",
    var F_Name_Latin: String? = "",
    var F_Pic01_ALT: String? = "",
    var F_Pic01_URL: String? = "",
    var F_Pic02_ALT: String? = "",
    var F_Pic02_URL: String? = "",
    var F_Pic03_ALT: String? = "",
    var F_Pic03_URL: String? = "",
    var F_Pic04_ALT: String? = "",
    var F_Pic04_URL: String? = "",
    var F_Summary: String? = "",
    var F_Update: String? = "",
    var F_Vedio_URL: String? = "",
    var F_Voice01_ALT: String? = "",
    var F_Voice01_URL: String? = "",
    var F_Voice02_ALT: String? = "",
    var F_Voice02_URL: String? = "",
    var F_Voice03_ALT: String? = "",
    var F_Voice03_URL: String? = "",
    var F_pdf01_ALT: String? = "",
    var F_pdf01_URL: String? = "",
    var F_pdf02_ALT: String? = "",
    var F_pdf02_URL: String? = "",
    var _full_count: String? = "",
    var _id: Int = 0,
    var F_Name_Ch: String? = "",
    var rank: Double = 0.0
): Parcelable{
    companion object {

        private val TAG: String = javaClass.simpleName

        @JvmStatic
        @BindingAdapter("plantImage")
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