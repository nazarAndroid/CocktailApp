package com.example.android.cocktaildb.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.cocktaildb.common.LiveDataNetworkStatus
import com.example.android.cocktaildb.ui.main.MainViewModel
import com.example.android.cocktaildb.R
import com.example.android.cocktaildb.model.Filter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.filters_layout.*

class FiltersFragment : Fragment() {

    lateinit var filterAdapter: FiltersAdapter
    var viewModel: MainViewModel =
        MainViewModel()
    lateinit var snackbar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filters_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snackbar = Snackbar.make(view, "Error Connection. Try again later", Snackbar.LENGTH_INDEFINITE)

        filterAdapter = FiltersAdapter(object: FiltersAdapter.FilterListener{
            override fun onFilterClick(filter: Filter) {
                    viewModel.updateFilter(filter)
            }
        })
        recycler_filters.adapter = filterAdapter

        LiveDataNetworkStatus(
            requireContext()
        ).observe(viewLifecycleOwner, Observer {
            if(it){
                if(viewModel.filtersLiveData.value.isNullOrEmpty()){
                    viewModel.loadFilters()
                }
                snackbar.dismiss()
            }
            else{
                snackbar.show()
            }
        })

        apply_button.setOnClickListener {
            returnDrinksFragment()
        }
        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        viewModel.filtersLiveData.observe(viewLifecycleOwner, Observer {
                progressLoad.visibility = View.INVISIBLE
                filterAdapter.setAllFilters(it)
        })
    }
    private fun returnDrinksFragment(){
        viewModel.reloadItems()
    }
}