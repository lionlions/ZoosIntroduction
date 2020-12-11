package com.cindy.zoosintroduction.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cindy.zoosintroduction.R
import com.cindy.zoosintroduction.databinding.FragmentZoosListBinding
import com.cindy.zoosintroduction.viewmodel.ViewModelFactory
import com.cindy.zoosintroduction.viewmodel.ZoosListViewModel

class ZoosListFragment : Fragment() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mZoosListViewModel: ZoosListViewModel
    private lateinit var mFragmentZoosListBinding: FragmentZoosListBinding
    private var mAdapter: ZoosListAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mFragmentZoosListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_zoos_list, container, false)

        processViewDataBinding()
        processRecyclerView()
        processViewModel()

        return mFragmentZoosListBinding.root
    }

    fun processViewDataBinding(){
        mZoosListViewModel = ViewModelProvider(this, ViewModelFactory()).get(ZoosListViewModel::class.java)
        mFragmentZoosListBinding.run {
            viewModel = mZoosListViewModel
            lifecycleOwner = this@ZoosListFragment
        }
    }

    fun processRecyclerView(){
        mAdapter = ZoosListAdapter(mZoosListViewModel)
        mFragmentZoosListBinding.vZoosList.adapter = mAdapter
    }

    fun processViewModel(){
        mZoosListViewModel.mZoosListLiveData.observe(this, Observer {
            if(mAdapter!=null){
                mAdapter!!.notifyDataSetChanged()
            }
        })
        mZoosListViewModel.mZoosListSelectItemLiveData.observe(this, Observer {
            Log.w(TAG, "name: ${it.E_Name}")
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.)
        })
    }
}