package id.nerdstudio.moviecatalogue.ui.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.ui.movie.MovieViewModel
import id.nerdstudio.moviecatalogue.ui.tv.TvShowViewModel
import id.nerdstudio.moviecatalogue.util.observe
import id.nerdstudio.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private var type: Type = Type.MOVIE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            type = Type.values()[it.getInt(ARG_TYPE)]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            val factory = ViewModelFactory.getInstance(it.application)
            val viewModel = ViewModelProviders.of(this, factory)
                .get(
                    if (type == Type.MOVIE) MovieViewModel::class.java
                    else TvShowViewModel::class.java
                )

            when (viewModel) {
                is MovieViewModel -> viewModel.getMovies().observe(this, ::notifyData)
                is TvShowViewModel -> viewModel.getTvShows().observe(this, ::notifyData)
            }

            val adapter = ListAdapter(it, listOf(), type)
            val recyclerView = recycler_view
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            recyclerView.setHasFixedSize(true)
        }
    }

    private fun notifyData(items: List<Item>) {
        hideLoading()
        val adapter = (recycler_view.adapter as ListAdapter)
        adapter.setData(items)
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        shimmer_loading.startShimmerAnimation()
    }

    override fun onPause() {
        shimmer_loading.stopShimmerAnimation()
        super.onPause()
    }

    private fun showLoading() {
        recycler_view.visibility = View.GONE
        shimmer_loading.startShimmerAnimation()
        shimmer_loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        recycler_view.visibility = View.VISIBLE
        shimmer_loading.stopShimmerAnimation()
        shimmer_loading.visibility = View.GONE
    }

    companion object {
        const val ARG_TYPE = "type"

        @JvmStatic
        fun newInstance(type: Type) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TYPE, type.ordinal)
                }
            }
    }

}
