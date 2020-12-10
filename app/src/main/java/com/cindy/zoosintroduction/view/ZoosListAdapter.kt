package com.cindy.zoosintroduction.view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cindy.zoosintroduction.BuildConfig
import com.cindy.zoosintroduction.databinding.ItemZoosListBinding
import com.cindy.zoosintroduction.model.Zoo
import com.cindy.zoosintroduction.viewmodel.ZoosListViewModel

class ZoosListAdapter(private val mViewModel: ZoosListViewModel): RecyclerView.Adapter<ZoosListAdapter.ViewHolder>() {

    private val TAG: String = javaClass.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== getItemCount =====")
        return mViewModel.mZoosListLiveData.value?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(BuildConfig.DEBUG)Log.v(TAG, "===== onBindViewHolder =====")
        if(BuildConfig.DEBUG)Log.i(TAG, "position: $position")
        if(mViewModel.mZoosListLiveData.value!=null){
            val zoo: Zoo = mViewModel.mZoosListLiveData.value!![position]
            holder.bind(mViewModel, zoo)
        }
    }

    class ViewHolder private constructor(private val mBinding: ItemZoosListBinding): RecyclerView.ViewHolder(mBinding.root){
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemZoosListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
        fun bind(viewModel: ZoosListViewModel, zoo: Zoo){
            mBinding.viewModel = viewModel
            mBinding.zoo = zoo
            mBinding.executePendingBindings()
        }
    }

}