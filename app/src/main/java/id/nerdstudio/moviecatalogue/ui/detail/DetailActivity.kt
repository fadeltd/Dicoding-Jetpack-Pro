package id.nerdstudio.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import id.nerdstudio.moviecatalogue.ui.main.PageType
import id.nerdstudio.moviecatalogue.util.*
import id.nerdstudio.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.shimmer_cast.*
import kotlinx.android.synthetic.main.shimmer_genre.*
import kotlinx.android.synthetic.main.shimmer_movie_detail.*
import kotlinx.android.synthetic.main.shimmer_movie_similar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var type: Type
    private lateinit var pageType: PageType
    private lateinit var movie: Movie
    private lateinit var tvShow: TvShow
    private var menu: Menu? = null

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
            type = it.getSerializable(ARG_TYPE) as Type
            pageType = it.getSerializable(ARG_PAGE_TYPE) as PageType
            viewModel.id = id
            viewModel.type = type

            if (pageType == PageType.TEST) {
                if (type == Type.MOVIE) {
                    viewModel.getMovieContent().observe(this) { catalogue ->
                        populate(catalogue)
                    }
                } else {
                    viewModel.getTvShowContent().observe(this) { catalogue ->
                        populate(catalogue)
                    }
                }
            } else {
                showLoading(shimmer_loading_cast, loading_cast_layout, movie_cast_layout)
                viewModel.getCast(type).observe(this) { cast ->
                    populateCast(cast)
                }
                showLoading(shimmer_loading_movie_similar, loading_movie_similar_layout, movie_similar_layout)
                if (type == Type.MOVIE) {
                    viewModel.getSimilarMovies().observe(this) { catalogue ->
                        populateSimilar(catalogue)
                    }
                } else {
                    viewModel.getSimilarTvShows().observe(this) { catalogue ->
                        populateSimilar(catalogue)
                    }
                }

                val layout = shimmer_loading_movie_detail.getChildAt(0) as LinearLayout
                for (i in 0 until layout.childCount) {
                    layout.getChildAt(i).visibility = View.VISIBLE
                }
                showLoading(shimmer_loading_movie_detail, loading_movie_detail_layout, movie_detail_layout)
                showLoading(shimmer_loading_genre, loading_genre_layout, movie_genres_layout)
                viewModel.getDetail(type).observe(this) { catalogue ->
                    catalogue as Catalogue
                    populate(catalogue)
                    populateGenre(catalogue.genres.asList())
                }
            }
        }
    }

    private fun populate(catalogue: Catalogue) {
        catalogue.run {
            var year = ""
            if (this is Movie) {
                movie = this
                if (!releaseDate.isNullOrEmpty()) {
                    year = "(${releaseDate.parseDate().year})"
                }
                movie_release_date.text = releaseDate.toFormattedDate()
                movie_title.text = title
                supportActionBar?.title = "$title $year"
            } else if (this is TvShow) {
                tvShow = this
                if (!firstAirDate.isNullOrEmpty()) {
                    year = "(${firstAirDate.parseDate().year})"
                }
                movie_release_date.text = firstAirDate.toFormattedDate()
                movie_title.text = name
                supportActionBar?.title = "$title $year"
            }

            showLoading(loading_movie_poster, loading_movie_poster, movie_poster)
            posterPath?.run {
                EspressoIdlingResource.increment()
                if (!this.endsWith("jpg")) {
                    movie_poster.setImagePoster(posterPath)
                    EspressoIdlingResource.decrement()
                    hideLoading(loading_movie_poster, loading_movie_poster, movie_poster)
                } else {
                    val url = getImageUrl(this, W500)
                    Ion.with(this@DetailActivity)
                        .load(url)
                        .asBitmap()
                        .setCallback { e, result ->
                            hideLoading(loading_movie_poster, loading_movie_poster, movie_poster)
                            if (e == null) {
                                movie_poster.setImageBitmap(result)
                            }
                            EspressoIdlingResource.decrement()
                        }
                }
            }

            movie_description.text = overview
            movie_language.text = Locale(originalLanguage).displayName

            if (this is Movie) {
                movie_runtime.text = runtime?.toRuntime()
                val currency = NumberFormat.getCurrencyInstance(Locale.US)
                movie_revenue.text = currency.format(revenue)
                movie_budget.text = currency.format(budget)
                for (i in 0 until movie_detail_layout.childCount) {
                    movie_detail_layout.getChildAt(i).visibility = View.VISIBLE
                }
            } else if (this is TvShow) {
                if (episodeRunTime.isNotEmpty()) {
                    movie_runtime.text = episodeRunTime[0].toRuntime()
                    movie_runtime_label.visibility = View.VISIBLE
                    movie_runtime.visibility = View.VISIBLE
                }
            }

            hideLoading(shimmer_loading_movie_detail, loading_movie_detail_layout, movie_detail_layout)

            GlobalScope.launch {
                val state = withContext(Dispatchers.IO) {
                    viewModel.isFavoriteMovie(id)
                }
                println("$id $state")
                setFavoriteState(state)
            }
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

    private fun populateSimilar(catalogue: List<Catalogue>) {
        movie_similar_label.text = "${type.value.capitalize()}s"
        movie_similar.adapter = SimilarAdapter(this, catalogue.distinct())
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

    private fun setFavoriteState(state: Boolean) {
        runOnUiThread {
            menu?.let {
                it.findItem(R.id.menu_favorite).icon = ContextCompat.getDrawable(
                    this, if (state) {
                        R.drawable.ic_heart_full
                    } else {
                        R.drawable.ic_heart_empty
                    }
                )
            }
        }
    }

    override fun onCreateOptionsMenu(_menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, _menu)
        menu = _menu
        return super.onCreateOptionsMenu(_menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.menu_favorite -> {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        var state = false
                        if (::movie.isInitialized) {
                            if (viewModel.isFavoriteMovie(movie.id)) {
                                viewModel.removeFavoriteMovie(movie)
                            } else {
                                viewModel.addToFavorite(movie)
                                state = true
                            }
                        } else if (::tvShow.isInitialized) {
                            if (viewModel.isFavoriteTvShow(tvShow.id)) {
                                viewModel.removeFavoriteTvShow(tvShow)
                            } else {
                                viewModel.addToFavorite(tvShow)
                                state = true
                            }
                        }
                        setFavoriteState(state)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ARG_ID = "id"
        const val ARG_TYPE = "type"
        const val ARG_PAGE_TYPE = "page_type"
    }
}
