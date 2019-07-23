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
import id.nerdstudio.moviecatalogue.api.ApiLoader
import id.nerdstudio.moviecatalogue.data.entity.Item
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.ui.main.PageType
import id.nerdstudio.moviecatalogue.ui.movie.FavoriteMovieViewModel
import id.nerdstudio.moviecatalogue.ui.movie.MovieViewModel
import id.nerdstudio.moviecatalogue.ui.tv.FavoriteTvShowViewModel
import id.nerdstudio.moviecatalogue.ui.tv.TvShowViewModel
import id.nerdstudio.moviecatalogue.util.observe
import id.nerdstudio.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {
    private var type: Type = Type.MOVIE
    private var pageType: PageType = PageType.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            type = Type.values()[it.getInt(ARG_TYPE)]
            pageType = PageType.values()[it.getInt(ARG_TYPE)]
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
            val factory = ViewModelFactory.getInstance(ApiLoader(this), it.application)
            val viewModel = ViewModelProviders.of(this, factory)
                .get(
                    if (pageType == PageType.HOME) {
                        if (type == Type.MOVIE) MovieViewModel::class.java
                        else TvShowViewModel::class.java
                    } else {
                        if (type == Type.MOVIE) FavoriteMovieViewModel::class.java
                        else FavoriteTvShowViewModel::class.java
                    }
                )
            if (pageType == PageType.HOME) {
                when (viewModel) {
//                    is MovieViewModel -> viewModel.getMoviesRemote().observe(this, ::notifyMovie)
                    is MovieViewModel -> viewModel.getMovies().observe(this, ::notifyData)
                    is TvShowViewModel -> viewModel.getTvShows().observe(this, ::notifyData)
                }
            } else {
                when (viewModel) {
                    is FavoriteMovieViewModel -> viewModel.getFavoriteMovies().observe(this, ::notifyMovie)
                    is FavoriteTvShowViewModel -> viewModel.getFavoriteTvShows().observe(this, ::notifyTvShow)
                }
            }

            val adapter = ListAdapter(it, type)
            val recyclerView = recycler_view
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            recyclerView.setHasFixedSize(true)
        }
    }

    private fun notifyMovie(movies: List<Movie>) {
        hideLoading()
        val adapter = (recycler_view.adapter as ListAdapter)
        adapter.setMovieData(movies)
        adapter.notifyDataSetChanged()
    }

    private fun notifyTvShow(tvShows: List<TvShow>) {
        hideLoading()
        val adapter = (recycler_view.adapter as ListAdapter)
        adapter.setTvShowData(tvShows)
        adapter.notifyDataSetChanged()
    }

    private fun notifyData(items: List<Item>) {
        hideLoading()
        val adapter = (recycler_view.adapter as ListAdapter)
        adapter.setData(items)
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
//        shimmer_loading.startShimmerAnimation()
        showLoading()
    }

    override fun onPause() {
//        shimmer_loading.stopShimmerAnimation()
        hideLoading()
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
        private const val ARG_PAGE_TYPE = "page_type"

        @JvmStatic
        fun newInstance(type: Type, pageType: PageType) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TYPE, type.ordinal)
                    putInt(ARG_PAGE_TYPE, pageType.ordinal)
                }
            }
    }
}
