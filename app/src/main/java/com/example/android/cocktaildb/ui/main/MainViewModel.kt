package com.example.android.cocktaildb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.cocktaildb.common.Event
import com.example.android.cocktaildb.model.BaseItem
import com.example.android.cocktaildb.model.DrinkItem
import com.example.android.cocktaildb.model.Filter
import com.example.android.cocktaildb.model.HeaderItem


class MainViewModel : ViewModel() {
    private val repository: MainRepository =
        MainRepository()
    var uiDataListLiveData = MediatorLiveData<Event<ArrayList<BaseItem>>>()
    var filtersLiveData = MediatorLiveData<ArrayList<Filter>>()
    var uiDataList = arrayListOf<BaseItem>()
    var currentFilterPosition = 0

    init {
        loadFilters()
        uiDataListLiveData.addSource(filtersLiveData) {
            loadInitial()
            uiDataListLiveData.removeSource(filtersLiveData)
        }
    }
    fun loadFilters(){
        val allFiltersList = repository.loadFilters()
        filtersLiveData.addSource(allFiltersList) {
            filtersLiveData.value = it.getFilters()
            if(filtersLiveData.value!!.isNotEmpty()) {
                filtersLiveData.removeSource(allFiltersList)
            }
        }
    }

    fun loadMore() {
        currentFilterPosition++
        filtersLiveData.value?.filter { it.selected }
            ?.getOrNull(currentFilterPosition)?.let {
                loadNextPage(it)
            }
    }

    private fun loadNextPage(it: Filter) {
        val loadNextPageData =
            loadDrinksForCategory(it.strCategory)
        uiDataListLiveData.addSource(loadNextPageData) {
            uiDataList.addAll(it!!)
            uiDataListLiveData.removeSource(loadNextPageData)
            uiDataListLiveData.value = Event.success(uiDataList)
        }
    }

    fun reloadItems() {
        loadInitial()
    }

    private fun loadInitial() {
        uiDataListLiveData.value = Event.loading()
        uiDataList.clear()
        uiDataListLiveData.value =  Event.success(uiDataList)
        currentFilterPosition = 0
        filtersLiveData.value?.firstOrNull { it.selected }?.let {
            loadNextPage(it)
        }
    }

    private fun loadDrinksForCategory(category: String): LiveData<ArrayList<BaseItem>> {
        val drinks = repository.loadData(category)
        return Transformations.map(drinks) {
            val drinksList = it.drinks
            val list = arrayListOf<BaseItem>()
            list.add(HeaderItem(category))
            list.addAll(drinksList.map { DrinkItem(it) })
            list
        }
    }

    fun isLastPage(): Boolean {
        return currentFilterPosition == filtersLiveData.value?.size ?: 0
    }

    fun updateFilter(filter: Filter) {
        filter.selected = !filter.selected
        filtersLiveData.value = filtersLiveData.value
    }
}