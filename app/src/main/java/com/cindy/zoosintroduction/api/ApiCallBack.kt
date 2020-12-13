package com.cindy.zoosintroduction.api

import com.cindy.zoosintroduction.model.PlantsInfoModel

interface ApiCallBack {
    fun onGetPlantsInfoDone(plantsInfoModel: PlantsInfoModel){}
    fun onApiFailed(errorMessage: String?)
}