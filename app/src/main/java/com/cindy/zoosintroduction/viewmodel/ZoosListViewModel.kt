package com.cindy.zoosintroduction.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.cindy.zoosintroduction.BuildConfig
import com.cindy.zoosintroduction.api.ApiRepository
import com.cindy.zoosintroduction.model.Zoo
import com.cindy.zoosintroduction.model.ZoosModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ZoosListViewModel(): ViewModel() {

    private val TAG: String = javaClass.simpleName
    private val DOCUMENT_ID: String = "19CrrOJVyS3e32Gb9GiR"

    private var mFirebaseFirestore: FirebaseFirestore? = null
    private var mDocumentData: Map<String, Any?>? = null
    private var mZoosModel: ZoosModel? = null

    var mZoosListLiveData: MutableLiveData<List<Zoo>> = MutableLiveData()
    var mZoosListSelectItemLiveData: SingleLiveData<Zoo> = SingleLiveData()
    val isZoosListEmpty: LiveData<Boolean> = Transformations.map(mZoosListLiveData){
        it.isNullOrEmpty()
    }

    var mErrorMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        initializeCloudFirestore()
        readDataFromFireStore()
    }
    fun initializeCloudFirestore(){
        mFirebaseFirestore = FirebaseFirestore.getInstance()
    }

    fun readDataFromFireStore(){
        mFirebaseFirestore?.run{
            collection("zoos_introduction")
                .get()
                .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot> {
                    override fun onComplete(task: Task<QuerySnapshot>) {

                        if(task.isSuccessful){
                            if(task.result!=null){
                                for(document in task.result!!){
                                    Log.d(TAG, "Document id: $document.id")
                                    Log.d(TAG, "Document data: ${document.data}")
                                    if(document.id==DOCUMENT_ID){
                                        mDocumentData = document.data
                                        break
                                    }
                                }
                                processDocumentData()
                            }else{
                                // result is null
                                setErrorMessage("Result is empty")
                            }
                        }else{
                            setErrorMessage("Error when getting documents.", task.exception)
                        }

                    }
                })
        }
    }

    fun setErrorMessage(errorMessage: String?, tr: Throwable? = null){
        Log.w(TAG, errorMessage, tr)
        viewModelScope.launch {
            mErrorMessageLiveData.value = errorMessage
        }
    }

    fun processDocumentData(){
        if(mDocumentData!=null && mDocumentData!!.isNotEmpty()){
            val documentDataToJson: String = Gson().toJson(mDocumentData)
            mZoosModel = Gson().fromJson(documentDataToJson, ZoosModel::class.java)
            if(mZoosModel!=null){
                viewModelScope.launch {
                    if(mZoosModel!!.data!=null
                        && !mZoosModel!!.data!!.zoos_list.isNullOrEmpty()){
                        if(BuildConfig.DEBUG)Log.i(TAG, "update value")
                        mZoosListLiveData.value = mZoosModel!!.data!!.zoos_list
                    }
                }
            }
        }else{
            setErrorMessage("Document data is empty")
        }
    }

    fun onItemClick(view: View, zoo: Zoo){
        //Go to next zoo detail fragment
        Log.v(TAG, "item click!!")
        mZoosListSelectItemLiveData.value = zoo
    }

}