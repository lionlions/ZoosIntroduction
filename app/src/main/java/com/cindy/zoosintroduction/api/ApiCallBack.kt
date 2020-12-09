package com.cindy.zoosintroduction.api

import com.cindy.zoosintroduction.model.PlantsInfoModel

interface ApiCallBack {
    fun onGetPlantsInfoDone(castModel: PlantsInfoModel){}
    fun onApiFailed(errorMessage: String?)
}