package com.cindy.zoosintroduction.retrofit

import com.cindy.zoosintroduction.model.PlantsInfoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class GetPlantsList {

    interface ApiService{
        @GET("api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14?scope=resourceAquire")
        fun getPlantsList(@Query("q") query: String,
                          @Query("limit") limit: Int,
                          @Query("offset") offset: Int): Call<String?>
    }

}