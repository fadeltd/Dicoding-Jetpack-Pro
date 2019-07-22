package id.nerdstudio.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.koushikdutta.ion.Ion
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.api.ApiLoader
import id.nerdstudio.moviecatalogue.config.AppConfig.PosterType.W500
import id.nerdstudio.moviecatalogue.config.AppConfig.getImageUrl
import id.nerdstudio.moviecatalogue.data.entity.*
import id.nerdstudio.moviecatalogue.ui.detail.cast.CastAdapter
import id.nerdstudio.moviecatalogue.ui.detail.genre.GenreAdapter
import id.nerdstudio.moviecatalogue.ui.detail.similar.SimilarAdapter
import id.nerdstudio.moviecatalogue.util.*
import id.nerdstudio.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.shimmer_cast.*
import kotlinx.android.synthetic.main.shimmer_genre.*
import kotlinx.android.synthetic.main.shimmer_movie_detail.*
import kotlinx.android.synthetic.main.shimmer_movie_similar.*
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var type: Type
    private lateinit var movie: Movie
    private lateinit var tvShow: TvShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(ApiLoader(this), application)
        viewModel = ViewModelProviders
            .of(this, factory)
            .get(DetailViewModel::class.java)

        intent.extras?.let {
            val id = it.getLong(ARG_ID)
            type = Type.values()[it.getInt(ARG_TYPE)]
            viewModel.id = id
            viewModel.type = type
            if (type == Type.MOVIE) {
                val layout = shimmer_loading_movie_detail.getChildAt(0) as LinearLayout
                for (i in 0 until layout.childCount) {
                    layout.getChildAt(i).visibility = View.VISIBLE
                }
                showLoading(shimmer_loading_movie_detail, loading_movie_detail_layout, movie_detail_layout)
                showLoading(shimmer_loading_genre, loading_genre_layout, movie_genres_layout)
                viewModel.getMovieDetail().observe(this) { movie ->
                    populateMovie(movie)
                    populateGenre(movie.genres.asList())
                }
                showLoading(shimmer_loading_cast, loading_cast_layout, movie_cast_layout)
                viewModel.getMovieCast().observe(this) { cast ->
                    populateCast(cast)
                }
                showLoading(shimmer_loading_movie_similar, loading_movie_similar_layout, movie_similar_layout)
                viewModel.getSimilarMovies().observe(this) { movies ->
                    populateSimilar(movies)
                }
            } else {
                viewModel.getItem()?.observe(this) { item ->
                    populateItem(item)
                }
            }
        }
    }

    private fun populateMovie(movie: Movie) {
        movie.run {
            var year = ""
            if (!releaseDate.isNullOrEmpty()) {
                year = "(${releaseDate.parseDate().year})"
            }
            movie_release_date.text = releaseDate.toFormattedDate()
            showLoading(loading_movie_poster, loading_movie_poster, movie_poster)
            posterPath?.run {
                val url = getImageUrl(this, W500)
                Ion.with(this@DetailActivity)
                    .load(url)
                    .asBitmap()
                    .setCallback { e, result ->
                        hideLoading(loading_movie_poster, loading_movie_poster, movie_poster)
                        if (e == null) {
                            movie_poster.setImageBitmap(result)
                        }
                    }
            }
            movie_title.text = title
            supportActionBar?.title = "$title $year"

            movie_description.text = overview

            movie_language.text = Locale(originalLanguage).displayName
            movie_runtime.text = runtime?.toRuntime()
            val currency = NumberFormat.getCurrencyInstance(Locale.US)
            movie_revenue.text = currency.format(revenue)
            movie_budget.text = currency.format(budget)

            for (i in 0 until movie_detail_layout.childCount) {
                movie_detail_layout.getChildAt(i).visibility = View.VISIBLE
            }

            hideLoading(shimmer_loading_movie_detail, loading_movie_detail_layout, movie_detail_layout)
        }
    }

    private fun populateCast(cast: List<Cast>) {
        movie_cast.adapter = CastAdapter(this, cast)
        movie_cast.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        hideLoading(shimmer_loading_cast, loading_cast_layout, movie_cast_layout)
    }

    private fun populateGenre(genres: List<Genre>) {
        movie_genre.adapter = GenreAdapter(this, genres)
        movie_genre.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        hideLoading(shimmer_loading_genre, loading_genre_layout, movie_genres_layout)
    }

    private fun populateSimilar(movies: List<Movie>) {
        movie_similar.adapter = SimilarAdapter(this, movies.distinct())
        movie_similar.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        hideLoading(shimmer_loading_movie_similar, loading_movie_similar_layout, movie_similar_layout)
    }

    private fun showLoading(layout: ShimmerFrameLayout, shimmerContainer: View, view: View) {
        view.visibility = View.GONE
        layout.startShimmer()
        shimmerContainer.visibility = View.VISIBLE
    }

    private fun hideLoading(layout: ShimmerFrameLayout, shimmerContainer: View, view: View) {
        layout.stopShimmerAnimation()
        shimmerContainer.visibility = View.GONE
        view.visibility = View.VISIBLE
    }

    private fun populateItem(item: Item) {
        item.run {
            var year = ""
            if (!releaseDate.isNullOrEmpty()) {
                year = "(${releaseDate.parseDate().year})"
            }
            movie_release_date.text = releaseDate.toFormattedDate()
            // movie_rating.text = voteAverage.toString()

            // showLoading(loading_movie_poster, movie_poster)
            if (!posterPath.isNullOrEmpty()) {
                movie_poster.setImagePoster(posterPath)
                hideLoading(loading_movie_poster, loading_movie_poster, movie_poster)
            }
            movie_title.text = title
            supportActionBar?.title = "$title $year"
            movie_description.text = overview

            hideLoading(shimmer_loading_movie_detail, loading_movie_detail_layout, movie_detail_layout)
        }
    }

    override fun onResume() {
        super.onResume()
        shimmer_loading_movie_detail.startShimmer()
        loading_movie_poster.startShimmer()
        shimmer_loading_cast.startShimmer()
        shimmer_loading_genre.startShimmer()
        shimmer_loading_movie_similar.startShimmer()
    }

    override fun onPause() {
        shimmer_loading_movie_detail.stopShimmerAnimation()
        loading_movie_poster.stopShimmerAnimation()
        shimmer_loading_cast.stopShimmerAnimation()
        shimmer_loading_genre.stopShimmerAnimation()
        shimmer_loading_movie_similar.stopShimmerAnimation()
        super.onPause()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_favorite -> {
                if (type == Type.MOVIE) {
                    viewModel.addToFavorite(movie)
                } else {
                    viewModel.addToFavorite(tvShow)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ARG_ID = "id"
        const val ARG_TYPE = "type"
    }
}
