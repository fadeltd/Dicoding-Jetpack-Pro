package id.nerdstudio.moviecatalogue.ui.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.ui.movie.MovieViewModel
import id.nerdstudio.moviecatalogue.ui.tv.TvShowViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import androidx.lifecycle.ViewModelProviders

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
        if (activity != null) {
            val viewModel = ViewModelProviders.of(this).get(
                if (type == Type.MOVIE) MovieViewModel::class.java
                else TvShowViewModel::class.java
            )
            val list = when (viewModel) {
                is MovieViewModel -> viewModel.getMovies()
                is TvShowViewModel -> viewModel.getTvShows()
                else -> listOf()
            }

            val recyclerView = recycler_view
            context?.run {
                recyclerView.adapter = ListAdapter(this, list, type)
                recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                recyclerView.setHasFixedSize(true)
            }
        }
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