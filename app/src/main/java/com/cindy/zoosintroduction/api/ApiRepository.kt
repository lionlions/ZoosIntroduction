package com.cindy.zoosintroduction.api

import android.util.Log
import com.cindy.zoosintroduction.BuildConfig
import com.cindy.zoosintroduction.model.PlantsInfoModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository {

    private val TAG: String = javaClass.simpleName
    private var mApiCallback: ApiCallBack? = null

    fun callGetPlantsList(query: String, limit:Int, offset: Int, apiCallBack: ApiCallBack){
        mApiCallback = apiCallBack
        val apiController: ApiController? = ApiController.getInstance()
        if(apiController!=null){
            apiController.getPlantsList(ApiConfig.URL_DOMAIN, query, limit, offset, object: Callback<String?>{
                override fun onFailure(call: Call<String?>, t: Throwable) {
                    returnErrorMessage(t.message)
                }

                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    if(response.isSuccessful){
                        if(response.body()!=null){
                            var stringResponse: String = response.body() as String
                            if(stringResponse.isEmpty()){
                                returnErrorMessage("Response body is null or empty!!")
                                return
                            }
                            if (BuildConfig.DEBUG) Log.i(TAG, "before stringResponse: $stringResponse")
                            if(stringResponse.contains("F_Function＆Application")){
                                stringResponse = stringResponse.replace("F_Function＆Application","F_Function_Application")
                            }
                            if(stringResponse.contains("\uFEFFF_Name_Ch")){
                                stringResponse = stringResponse.replace("\uFEFFF_Name_Ch","F_Name_Ch")
                            }
                            if (BuildConfig.DEBUG) Log.w(TAG, "after stringResponse: $stringResponse")
                            val plantsInfoModel: PlantsInfoModel? = Gson().fromJson(stringResponse, PlantsInfoModel::class.java)
                            if(plantsInfoModel!=null){
                                apiCallBack.onGetPlantsInfoDone(plantsInfoModel)
                            }
                        }else{
                            returnErrorMessage("Error Code: ${ApiConfig.ERROR_CODE_RESPONSE_BODY_IS_NULL}")
                        }
                    }else{
                        returnErrorMessage("Error Code: ${ApiConfig.ERROR_CODE_RESPONSE_FAILED}")
                    }
                }

            })
        }
    }

    fun returnErrorMessage(errorMessage: String?){
        mApiCallback?.run {
            onApiFailed(errorMessage)
        }
    }

}