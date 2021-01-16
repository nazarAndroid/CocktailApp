package com.example.android.cocktaildb.ui.drinks

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.android.cocktaildb.R
import com.example.android.cocktaildb.model.*
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class DrinksListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: ArrayList<BaseItem> = ArrayList()


    private val HEADER_VIEW_TYPE = 1
    private val DRINK_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType){
            DRINK_VIEW_TYPE -> {
                DrinksViewHolder(inflater.inflate(R.layout.card_drink, parent, false))
            }
            else -> HeaderViewHolder(inflater.inflate(R.layout.card_category_main, parent, false))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is HeaderViewHolder){
            holder.bind(dataList[position] as HeaderItem)
        }
        else if(holder is DrinksViewHolder){
            holder.bind(dataList[position] as DrinkItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]){
            is HeaderItem -> HEADER_VIEW_TYPE
            else -> DRINK_VIEW_TYPE
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var headerItem:HeaderItem? = null
        private var categoryName: TextView = itemView.findViewById(R.id.name_category_header)

        fun bind(headerItem: HeaderItem){
            this.headerItem = headerItem
            categoryName.text = headerItem.title
        }
    }
    class DrinksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var imageDrink: ImageView = itemView.findViewById(R.id.image_drink)
        private var titleDrink: TextView = itemView.findViewById(R.id.name_drink)

        fun bind(drinkItem: DrinkItem) {
            val drink = drinkItem.drink
            titleDrink.text = drink.strDrink
            Picasso.get().load(drink.strDrinkThumb).into(imageDrink)

        }

    }

    fun setAllDrink(all_drinks: ArrayList<BaseItem>) {
        this.dataList = all_drinks
        notifyDataSetChanged()
    }
}