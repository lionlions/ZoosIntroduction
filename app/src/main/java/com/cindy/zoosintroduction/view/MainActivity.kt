package com.cindy.zoosintroduction.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cindy.zoosintroduction.R
import com.cindy.zoosintroduction.model.ZoosIntroduction
import com.cindy.zoosintroduction.model.ZoosModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName
    private val DOCUMENT_ID: String = "19CrrOJVyS3e32Gb9GiR"

    private var mFirebaseFirestore: FirebaseFirestore? = null
    private var mDocumentData: Map<String, Any?>? = null
    private var mZoosModel: ZoosModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                .addOnCompleteListener(object: OnCompleteListener<QuerySnapshot>{
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
                                Log.w(TAG, "result is null")
                            }
                        }else{
                            Log.w(TAG, "Error when getting documents.", task.exception)
                        }

                    }
                })
        }
    }

    fun processDocumentData(){
        if(mDocumentData!=null && mDocumentData!!.isNotEmpty()){
            val documentDataToJson: String = Gson().toJson(mDocumentData)
            mZoosModel = Gson().fromJson(documentDataToJson, ZoosModel::class.java)
        }
    }

}