package com.cindy.zoosintroduction.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cindy.zoosintroduction.api.ApiRepository

class ViewModelFactory(
    private val repository: ApiRepository? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        modelClass.run {
            when {
                isAssignableFrom(ZoosListViewModel::class.java) -> {
                    return ZoosListViewModel() as T
                }
                isAssignableFrom(PlantsListViewModel::class.java) -> {
                    return PlantsListViewModel(repository) as T
                }
                else -> {
                    throw IllegalAccessException("Unknown ViewModel Class")
                }
            }
        }
    }
}