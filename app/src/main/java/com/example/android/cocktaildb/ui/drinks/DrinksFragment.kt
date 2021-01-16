package com.example.android.cocktaildb.ui.drinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.cocktaildb.common.LiveDataNetworkStatus
import com.example.android.cocktaildb.ui.main.MainViewModel
import com.example.android.cocktaildb.R
import com.example.android.cocktaildb.ui.main.RecyclerViewPaginate
import com.example.android.cocktaildb.common.Status
import com.example.android.cocktaildb.ui.filter.FiltersFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.drinks_layout.*


class DrinksFragment : Fragment() {

    var viewModel: MainViewModel =
        MainViewModel()
    var drinksListAdapter = DrinksListAdapter()
    lateinit var snackbar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drinks_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragment = FiltersFragment()

        snackbar = Snackbar.make(view, "Error Connection. Try again later", Snackbar.LENGTH_INDEFINITE)

        filter_button.setOnClickListener {
            val transaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            transaction.add(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        LiveDataNetworkStatus(
            requireContext()
        ).observe(viewLifecycleOwner, Observer {
            if(it){
                snackbar.dismiss()
            }
            else{
                snackbar.show()
            }
        })

        recycler_drinks.adapter = drinksListAdapter

        viewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)

        viewModel.uiDataListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                progressLoad.visibility = View.INVISIBLE
                it.data?.let { it1 -> drinksListAdapter.setAllDrink(it1) }
            }
            else if(it.status == Status.LOADING){
                progressLoad.visibility = View.VISIBLE
            }

        })
        recycler_drinks.addOnScrollListener(object : RecyclerViewPaginate(recycler_drinks) {
            override val isLastPage: Boolean
                get() = viewModel.isLastPage()

            override fun loadMore(nextPage: Long) {
                viewModel.loadMore()
            }
        })

    }

}