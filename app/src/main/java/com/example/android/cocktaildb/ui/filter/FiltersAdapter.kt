package com.example.android.cocktaildb.ui.filter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.android.cocktaildb.R
import com.example.android.cocktaildb.model.Filter

class FiltersAdapter(private val listener: FilterListener) :
    RecyclerView.Adapter<FiltersAdapter.FiltersViewHolder>() {

    private var allFilterArray: ArrayList<Filter> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.filter_card, parent, false)
        return FiltersViewHolder(
            view, listener
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: FiltersViewHolder, position: Int) {
        holder.bind(allFilterArray[position])
    }

    override fun getItemCount(): Int {
        return allFilterArray.size
    }

    class FiltersViewHolder(itemView: View, listener: FilterListener) :
        RecyclerView.ViewHolder(itemView) {
        private var filter: Filter?
        private var filterCategory: TextView
        private var checkBox: CheckBox

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(filter: Filter) {
            this.filter = filter
            filterCategory.text = filter.strCategory
            checkBox.isChecked = filter.selected
        }

        init {
            filter = null
            filterCategory = itemView.findViewById(R.id.name_category)
            checkBox = itemView.findViewById(R.id.isSelectedBox)
            itemView.setOnClickListener {
                listener.onFilterClick(filter!!)
            }
            checkBox.setOnClickListener {
                listener.onFilterClick(filter!!)
            }
        }

    }

    fun setAllFilters(all_filters: ArrayList<Filter>) {
        this.allFilterArray = all_filters
        notifyDataSetChanged()
    }

    interface FilterListener {
        fun onFilterClick(filter: Filter)
    }

}