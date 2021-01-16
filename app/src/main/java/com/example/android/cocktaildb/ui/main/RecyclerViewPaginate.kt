package com.example.android.cocktaildb.ui.main

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE

abstract class RecyclerViewPaginate(recyclerView: RecyclerView) : RecyclerView.OnScrollListener() {

    private var currentPage: Long = 1L

    private val prefetchDistance = 10

    /*
     * This is a hack to ensure that the app is notified
     * only once to fetch more data. Since we use
     * scrollListener, there is a possibility that the
     * app will be notified more than once when user is
     * scrolling. This means there is a chance that the
     * same data will be fetched from the backend again.
     * This variable is to ensure that this does NOT
     * happen.
     * */
    private var endWithAuto = false

    /*
     * We pass the RecyclerView to the constructor
     * of this class to get the LayoutManager
     * */
    private val layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager

    private val nextPage: Long
        get() = ++currentPage

    abstract val isLastPage: Boolean

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING) {
            val totalItemCount = recyclerView.adapter?.itemCount ?: 0

            var firstLastVisibleItemPosition = 0
            if (layoutManager is LinearLayoutManager) {
                firstLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            } else if (layoutManager is GridLayoutManager) {
                firstLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            }

            //if(isLoading()) return
            if (isLastPage) return

            if (firstLastVisibleItemPosition + prefetchDistance >= totalItemCount) {
                if (!endWithAuto) {
                    endWithAuto = true
                    loadMore(nextPage)
                }
            } else {
                endWithAuto = false
            }
        }
    }

    fun reset() {
        currentPage = 1L
    }
    
    abstract fun loadMore(nextPage: Long)
}