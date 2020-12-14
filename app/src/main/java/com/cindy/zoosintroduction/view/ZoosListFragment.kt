package com.cindy.zoosintroduction.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.cindy.zoosintroduction.R
import com.cindy.zoosintroduction.databinding.FragmentZoosListBinding
import com.cindy.zoosintroduction.viewmodel.ViewModelFactory
import com.cindy.zoosintroduction.viewmodel.ZoosListViewModel
import kotlinx.android.synthetic.main.fragment_zoos_list.*

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
        mZoosListViewModel.mZoosListLiveData.observe(viewLifecycleOwner, Observer {
            if(mAdapter!=null){
                mAdapter!!.notifyDataSetChanged()
            }
        })
        mZoosListViewModel.mZoosListSelectItemLiveData.observe(viewLifecycleOwner, Observer {
            Log.w(TAG, "name: ${it.E_Name}")
            val bundle: Bundle = bundleOf(
                "title" to it.E_Name,
                "zoo" to it
            )
            val navController: NavController = findNavController()
            navController.navigate(R.id.nav_plants, bundle)
        })
        mZoosListViewModel.mErrorMessageLiveData.observe(viewLifecycleOwner, Observer {
            Log.w(TAG, "update error message")
            mFragmentZoosListBinding.vErrorMessage.visibility = View.VISIBLE
            if(mZoosListViewModel.isZoosListEmpty.value!=null
                && !mZoosListViewModel.isZoosListEmpty.value!!){
                mFragmentZoosListBinding.vZoosList.visibility = View.GONE
            }
        })
    }
}