package com.example.android.cocktaildb.model

open class BaseItem {}

data class HeaderItem(var title: String) : BaseItem()

data class DrinkItem(var drink: Drink) : BaseItem()