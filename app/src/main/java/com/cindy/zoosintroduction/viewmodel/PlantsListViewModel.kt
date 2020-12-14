package com.cindy.zoosintroduction.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.cindy.zoosintroduction.BuildConfig
import com.cindy.zoosintroduction.api.ApiCallBack
import com.cindy.zoosintroduction.api.ApiRepository
import com.cindy.zoosintroduction.model.PlantInfo
import com.cindy.zoosintroduction.model.PlantsInfoModel
import com.cindy.zoosintroduction.model.PlantsListResult
import com.cindy.zoosintroduction.model.Zoo
import kotlinx.coroutines.launch

class PlantsListViewModel(val mRepository: ApiRepository?): ViewModel() {

    private val TAG: String = javaClass.simpleName

    var mPlantsListLiveData: MutableLiveData<List<PlantInfo>> = MutableLiveData()
    var mTotalCount: MutableLiveData<Int> = MutableLiveData()
    var mPlantsList: ArrayList<PlantInfo> = ArrayList<PlantInfo>()
    var mPlantsListSelectItemLiveData: SingleLiveData<PlantInfo> = SingleLiveData()
    val isPlantsListEmpty: LiveData<Boolean> = Transformations.map(mPlantsListLiveData){
        it.isNullOrEmpty()
    }

    init {
        mPlantsListLiveData.value = ArrayList()
    }

    var mErrorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    fun getPlantsList(query: String?, limit:Int, offset: Int){
        if(BuildConfig.DEBUG)Log.v(TAG, "===== getPlantsList =====")
        if(BuildConfig.DEBUG)Log.d(TAG, "query: $query")
        if(BuildConfig.DEBUG)Log.d(TAG, "limit: $limit")
        if(BuildConfig.DEBUG)Log.d(TAG, "offset: $offset")
        if(query.isNullOrEmpty()){
            setErrorMessage("Query String should not be empty!!")
            return
        }
        mRepository?.callGetPlantsList(query, limit, offset, object:
            ApiCallBack {
            override fun onGetPlantsInfoDone(plantsInfoModel: PlantsInfoModel) {
                if(BuildConfig.DEBUG)Log.v(TAG, "===== onGetPlantsInfoDone =====")
                viewModelScope.launch {
                    if(plantsInfoModel.result!=null){
                        if(plantsInfoModel.result!!.count==null){
                            setErrorMessage("無任何資料")
                        }else{
                            mTotalCount.value = plantsInfoModel.result!!.count
                        }
                        if(!plantsInfoModel.result!!.results.isNullOrEmpty()){
                            if(BuildConfig.DEBUG)Log.i(TAG, "update value")
                            for(plantsInfo in plantsInfoModel.result!!.results!!){
                                mPlantsList.add(plantsInfo)
                            }
                            mPlantsListLiveData.value = mPlantsList
                        }
                    }else{
                        setErrorMessage("Something wrong when getting result")
                    }
                }
            }
            override fun onApiFailed(errorMessage: String?) {
                setErrorMessage(errorMessage)
            }
        })
    }

    fun onItemClick(view: View, plantInfo: PlantInfo){
        //Go to next zoo detail fragment
        Log.v(TAG, "item click!!")
        mPlantsListSelectItemLiveData.value = plantInfo
    }

    fun setErrorMessage(errorMessage: String?, tr: Throwable? = null){
        Log.w(TAG, errorMessage, tr)
        viewModelScope.launch {
            mErrorMessageLiveData.value = errorMessage
        }
    }

}