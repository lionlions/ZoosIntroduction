package com.cindy.zoosintroduction.view

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.cindy.zoosintroduction.R
import com.cindy.zoosintroduction.databinding.FragmentPlantInfoBinding
import com.cindy.zoosintroduction.model.PlantInfo
import com.cindy.zoosintroduction.viewmodel.ViewModelFactory
import com.cindy.zoosintroduction.viewmodel.ZoosListViewModel
import kotlinx.android.synthetic.main.fragment_plant_info.*

fun Fragment.getHtmlSpannedString(@StringRes id: Int, vararg formatArgs: Any?): Spanned = getString(id, *formatArgs).toHtmlSpan()
fun String.toHtmlSpan(): Spanned = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
}else{
    Html.fromHtml(this)
}

class PlantInfoFragment : Fragment() {

    private val TAG: String = javaClass.simpleName
    private lateinit var mFragmentPlantInfoBinding: FragmentPlantInfoBinding
    private var mPlantInfo: PlantInfo? = null
    private var mAdapter: ZoosListAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mFragmentPlantInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_plant_info, container, false)

        processArguments()
        processViewDataBinding()
        processIntroduction()

        return mFragmentPlantInfoBinding.root
    }

    fun processArguments() {
        mPlantInfo = requireArguments().getParcelable("plantInfo")
    }

    fun processViewDataBinding(){
        mFragmentPlantInfoBinding.run {
            plantInfo = mPlantInfo
            lifecycleOwner = this@PlantInfoFragment
        }
    }

    fun processIntroduction(){
        mFragmentPlantInfoBinding.contentText.let {
            it.movementMethod = ScrollingMovementMethod.getInstance()
            mPlantInfo?.run {
                if(F_Brief!=null){
                    it.text = String.format(getString(R.string.brief), F_Brief)
                }
                if(F_Family!=null){
                    it.text = "${it.text}${String.format(getString(R.string.family), F_Family)}"
                }
                if(F_Location!=null){
                    it.text = "${it.text}${String.format(getString(R.string.location), F_Location)}"
                }
                if(F_Feature!=null){
                    it.text = "${it.text}${String.format(getString(R.string.feature), F_Feature)}"
                }
                if(F_Function_Application!=null){
                    it.text = "${it.text}${String.format(getString(R.string.function), F_Function_Application)}"
                }
                if(F_Update!=null){
                    it.text = "${it.text}${String.format(getString(R.string.last_update), F_Update)}"
                }
            }
        }
    }

}