package com.example.android.cocktaildb.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.cocktaildb.common.Api
import com.example.android.cocktaildb.common.NetworkService
import com.example.android.cocktaildb.model.ResultDrinks
import com.example.android.cocktaildb.model.ResultFilter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository {
    var api: Api = NetworkService.retrofitService()
    fun loadFilters():MutableLiveData<ResultFilter> {
        val filterLiveData = MutableLiveData<ResultFilter>()
        api.getFilters().enqueue(object : Callback<ResultFilter> {
            override fun onFailure(call: Call<ResultFilter>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<ResultFilter>,
                response: Response<ResultFilter>
            ) {
                val responseData = response.body()
                responseData?.getFilters()?.forEach {
                    it.selected = true
                }
                filterLiveData.postValue(responseData)
            }
        })
        return filterLiveData
    }

    fun loadData(category: String): LiveData<ResultDrinks>{
        val drinkLiveData = MutableLiveData<ResultDrinks>()
        api.getDrinksWithCategory(category).enqueue(object : Callback<ResultDrinks> {
            override fun onFailure(call: Call<ResultDrinks>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<ResultDrinks>,
                response: Response<ResultDrinks>
            ) {
                drinkLiveData.postValue(response.body())
            }
        })
        return drinkLiveData
    }


}