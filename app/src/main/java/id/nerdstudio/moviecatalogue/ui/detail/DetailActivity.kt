package id.nerdstudio.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.util.observe
import id.nerdstudio.moviecatalogue.util.parseDate
import id.nerdstudio.moviecatalogue.util.setImagePoster
import id.nerdstudio.moviecatalogue.util.toFormattedDate
import id.nerdstudio.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.shimmer_cast.*
import kotlinx.android.synthetic.main.shimmer_genre.*
import kotlinx.android.synthetic.main.shimmer_movie_detail.*
import kotlinx.android.synthetic.main.shimmer_movie_similar.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(application)
        val viewModel = ViewModelProviders
            .of(this, factory)
            .get(DetailViewModel::class.java)

        intent.extras?.let {
            val id = it.getLong(ARG_ID)
            val type = it.getInt(ARG_TYPE)
            viewModel.id = id
            viewModel.type = Type.values()[type]
            viewModel.getItem()?.observe(this) { item ->
                populateItem(item)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
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

    companion object {
        const val ARG_ID = "id"
        const val ARG_TYPE = "type"
    }
}

fun ShimmerFrameLayout.startShimmer() {
    if (visibility == View.VISIBLE) {
        this.startShimmerAnimation()
    }
}