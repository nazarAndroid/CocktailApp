package com.example.android.cocktaildb.model

data class ResultFilter(
    private var drinks: ArrayList<Filter>

){
    fun getFilters() = drinks
}