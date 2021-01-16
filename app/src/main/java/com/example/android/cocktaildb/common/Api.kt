package com.example.android.cocktaildb.common

import com.example.android.cocktaildb.model.Drink
import com.example.android.cocktaildb.model.ResultDrinks
import com.example.android.cocktaildb.model.ResultFilter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("list.php?c=list")
    fun getFilters(): Call<ResultFilter>

    @GET("filter.php?")
    fun getDrinksWithCategory(
        @Query("c") category: String
    ): Call<ResultDrinks>
}