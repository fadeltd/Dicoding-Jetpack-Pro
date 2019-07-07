package id.nerdstudio.moviecatalogue.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.util.observe
import id.nerdstudio.moviecatalogue.util.parseDate
import id.nerdstudio.moviecatalogue.util.setImagePoster
import id.nerdstudio.moviecatalogue.util.toFormattedDate
import id.nerdstudio.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_detail.*

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

    private fun populateItem(item: Item) {
        item.run {
            var year = ""
            if (!releaseDate.isNullOrEmpty()) {
                year = "(${releaseDate.parseDate().year})"
            }
            movie_release_date.text = releaseDate.toFormattedDate()
            movie_rating.text = voteAverage.toString()
            if (!posterPath.isNullOrEmpty()) {
                movie_poster.setImagePoster(posterPath)
            }
            supportActionBar?.title = "$title $year"
            movie_description.text = overview
        }
    }

    companion object {
        const val ARG_ID = "id"
        const val ARG_TYPE = "type"
    }
}
