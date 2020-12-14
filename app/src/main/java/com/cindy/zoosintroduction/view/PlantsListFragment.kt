package com.cindy.zoosintroduction.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.cindy.zoosintroduction.R
import com.cindy.zoosintroduction.api.ApiRepository
import com.cindy.zoosintroduction.databinding.FragmentPlantsListBinding
import com.cindy.zoosintroduction.model.Zoo
import com.cindy.zoosintroduction.viewmodel.PlantsListViewModel
import com.cindy.zoosintroduction.viewmodel.ViewModelFactory

class PlantsListFragment : Fragment() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mPlantsListViewModel: PlantsListViewModel
    private lateinit var mFragmentPlantsListBinding: FragmentPlantsListBinding
    private var mZooInfo: Zoo? = null
    private val LIMIT_COUNT = 10
    private var mAdapter: PlantsListAdapter? = null
    private var mCurrentPage: Int = 0
    private var mTotalPlantsCount: Int = 0
    private var isLoadEnd: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFragmentPlantsListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_plants_list, container, false)

        processArguments()
        processViewDataBinding()
        processRecyclerView()
        processViewModel()

        return mFragmentPlantsListBinding.root
    }

    fun processArguments() {
        mZooInfo = requireArguments().getParcelable("zoo")
    }

    fun processViewDataBinding() {
        mPlantsListViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(ApiRepository())
            ).get(PlantsListViewModel::class.java)
        loadPlantsData()
        mFragmentPlantsListBinding.run {
            zoo = mZooInfo
            viewModel = mPlantsListViewModel
            lifecycleOwner = this@PlantsListFragment
        }
    }

    fun processRecyclerView() {
        mAdapter = PlantsListAdapter(mPlantsListViewModel)
        mFragmentPlantsListBinding.vPlantsList.run {
            adapter = mAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                    if(newState==RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)){
                        //Scroll to bottom
                        Log.v(TAG, "load next")
                        loadPlantsData()
                    }
                }
            })
        }
    }

    fun processViewModel() {
        mPlantsListViewModel.run {
            mTotalCount.observe(viewLifecycleOwner, Observer {
                mTotalPlantsCount = it
            })
            mPlantsListLiveData.observe(viewLifecycleOwner, Observer {
                if (mAdapter != null) {
                    mAdapter!!.notifyDataSetChanged()
                }
            })
            mPlantsListSelectItemLiveData.observe(viewLifecycleOwner, Observer {
                val navController: NavController = findNavController()
                val bundle: Bundle = bundleOf(
                    "title" to it.F_Name_Ch,
                    "plantInfo" to it
                )
                navController.navigate(R.id.nav_plant_info, bundle)
            })
            mErrorMessageLiveData.observe(viewLifecycleOwner, Observer {
                mFragmentPlantsListBinding.vErrorMessage.visibility = View.VISIBLE
                if(isPlantsListEmpty.value!=null
                    && !isPlantsListEmpty.value!!){
                    mFragmentPlantsListBinding.vPlantsList.visibility = View.GONE
                }
            })
        }
    }

    fun loadPlantsData(){
        val offset: Int = if(mCurrentPage*LIMIT_COUNT<=mTotalPlantsCount){
            mCurrentPage*LIMIT_COUNT
        }else{
            Toast.makeText(context, getString(R.string.the_end_of_data), Toast.LENGTH_SHORT).show()
            return
        }
        mCurrentPage++
        Log.i(TAG, "mCurrentPage: $mCurrentPage")
        Log.i(TAG, "offset: $offset")
        if(mZooInfo!=null && !mZooInfo!!.E_Name.isNullOrEmpty()){
            mPlantsListViewModel.getPlantsList(mZooInfo!!.E_Name, LIMIT_COUNT, offset)
        }
    }
}