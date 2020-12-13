package com.cindy.zoosintroduction.api

import android.util.Log
import com.cindy.zoosintroduction.BuildConfig
import com.cindy.zoosintroduction.model.PlantsInfoModel
import com.cindy.zoosintroduction.retrofit.GetPlantsList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ApiController {

    private val TAG: String = javaClass.simpleName

    companion object {
        private var mApiController: ApiController? = null
        fun getInstance(): ApiController? {
            if (mApiController == null) {
                mApiController =
                    ApiController()
            }
            return mApiController
        }
    }

    fun createRetrofit(url: String?): Retrofit? {
        // Check null
        if(url.isNullOrEmpty()){
            if(BuildConfig.DEBUG)Log.e(TAG, "url should not be null or empty")
            return null
        }

        var okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(logging).build()
        }
        val client: OkHttpClient = okHttpBuilder.build()
        return Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
    }

    fun getPlantsList(url: String?, query: String, limit: Int, offset: Int, callback: Callback<String?>) {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== getPlantsList =====")
        val retrofit: Retrofit? = createRetrofit(url)
        if(retrofit!=null){
            val apiService: GetPlantsList.ApiService = retrofit.create(
                GetPlantsList.ApiService::class.java)
            val call: Call<String?> = apiService.getPlantsList(query, limit, offset)
            call.enqueue(callback)
        }else{
            if(BuildConfig.DEBUG)Log.e(TAG, "Something went wrong!! Retrofit is null!!")
        }
    }

}