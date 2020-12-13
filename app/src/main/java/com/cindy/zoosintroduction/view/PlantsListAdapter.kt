package com.cindy.zoosintroduction.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cindy.zoosintroduction.BuildConfig
import com.cindy.zoosintroduction.databinding.ItemPlantsListBinding
import com.cindy.zoosintroduction.model.PlantInfo
import com.cindy.zoosintroduction.viewmodel.PlantsListViewModel

class PlantsListAdapter(private val mViewModel: PlantsListViewModel): RecyclerView.Adapter<PlantsListAdapter.ViewHolder>() {

    private val TAG: String = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== getItemCount =====")
        return mViewModel.mPlantsListLiveData.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== onBindViewHolder =====")
        if(BuildConfig.DEBUG)Log.i(TAG, "position: $position")
        if(mViewModel.mPlantsListLiveData.value!=null){
            val zoo: PlantInfo = mViewModel.mPlantsListLiveData.value!![position]
            holder.bind(mViewModel, zoo)
        }
    }

    class ViewHolder private constructor(private val mBinding: ItemPlantsListBinding): RecyclerView.ViewHolder(mBinding.root){
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPlantsListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
        fun bind(viewModel: PlantsListViewModel, plantInfo: PlantInfo){
            mBinding.viewModel = viewModel
            mBinding.plantsInfo = plantInfo
            mBinding.executePendingBindings()
        }
    }

}