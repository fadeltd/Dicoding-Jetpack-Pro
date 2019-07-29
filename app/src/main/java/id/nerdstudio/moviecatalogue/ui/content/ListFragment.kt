package id.nerdstudio.moviecatalogue.ui.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.api.ApiLoader
import id.nerdstudio.moviecatalogue.data.entity.Catalogue
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.ui.main.PageType
import id.nerdstudio.moviecatalogue.ui.movie.FavoriteMovieViewModel
import id.nerdstudio.moviecatalogue.ui.movie.MovieViewModel
import id.nerdstudio.moviecatalogue.ui.tv.FavoriteTvShowViewModel
import id.nerdstudio.moviecatalogue.ui.tv.TvShowViewModel
import id.nerdstudio.moviecatalogue.util.observe
import id.nerdstudio.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import androidx.lifecycle.Observer
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow

class ListFragment : Fragment() {
    private var type: Type = Type.MOVIE
    private var pageType: PageType = PageType.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            type = it.getSerializable(ARG_TYPE) as Type
            pageType = it.getSerializable(ARG_PAGE_TYPE) as PageType
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
                    if (pageType == PageType.FAVORITE) {
                        if (type == Type.MOVIE) FavoriteMovieViewModel::class.java
                        else FavoriteTvShowViewModel::class.java
                    } else {
                        if (type == Type.MOVIE) MovieViewModel::class.java
                        else TvShowViewModel::class.java
                    }
                )
            when (pageType) {
                PageType.HOME -> when (viewModel) {
                    is MovieViewModel -> viewModel.getMoviesRemote().observe(this, ::notifyData)
                    is TvShowViewModel -> viewModel.getTvShowsRemote().observe(this, ::notifyData)
                }
                PageType.FAVORITE -> when (viewModel) {
                    is FavoriteMovieViewModel -> viewModel.getFavoriteMovies().observe(this,
                        Observer<PagedList<Movie>> { catalogue ->
                            catalogue?.run {
                                notifyFavorite(this as PagedList<Catalogue>)
                            }
                        })
                    is FavoriteTvShowViewModel -> viewModel.getFavoriteTvShows().observe(
                        this,
                        Observer<PagedList<TvShow>> { catalogue ->
                            catalogue?.run {
                                notifyFavorite(this as PagedList<Catalogue>)
                            }
                        })
                }
                else -> when (viewModel) {
                    is MovieViewModel -> viewModel.getMovies().observe(this, ::notifyData)
                    is TvShowViewModel -> viewModel.getTvShows().observe(this, ::notifyData)
                }
            }

            val diffUtil = object : DiffUtil.ItemCallback<Catalogue>() {
                override fun areItemsTheSame(oldNote: Catalogue, newNote: Catalogue): Boolean {
                    return oldNote.id == newNote.id
                }

                override fun areContentsTheSame(oldNote: Catalogue, newNote: Catalogue): Boolean {
                    return oldNote.equals(newNote)
                }
            }
            val adapter = if (pageType == PageType.FAVORITE) {
                FavoriteListAdapter(it, type, pageType, diffUtil)
            } else {
                ListAdapter(it, type, pageType)
            }
            val recyclerView = recycler_view
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(it, RecyclerView.VERTICAL, false)
            recyclerView.setHasFixedSize(true)
        }
    }

    private fun notifyData(catalogue: List<Catalogue>) {
        hideLoading()
        val adapter = (recycler_view.adapter as ListAdapter)
        adapter.setData(catalogue)
        adapter.notifyDataSetChanged()
    }

    private fun notifyFavorite(catalogue: PagedList<Catalogue>) {
        val adapter = (recycler_view.adapter as FavoriteListAdapter)
        adapter.submitList(catalogue)
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
        if (recycler_view.adapter?.itemCount == 0) {
            recycler_view.visibility = View.GONE
            shimmer_loading.startShimmerAnimation()
            shimmer_loading.visibility = View.VISIBLE
        }
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
                    putSerializable(ARG_TYPE, type)
                    putSerializable(ARG_PAGE_TYPE, pageType)
                }
            }
    }
}
