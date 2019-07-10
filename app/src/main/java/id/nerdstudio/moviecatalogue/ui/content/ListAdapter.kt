package id.nerdstudio.moviecatalogue.ui.content

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koushikdutta.ion.Ion
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.config.AppConfig.getImageUrl
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Movie
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.ui.detail.DetailActivity
import id.nerdstudio.moviecatalogue.util.setImagePoster
import id.nerdstudio.moviecatalogue.util.toFormattedDate
import kotlinx.android.synthetic.main.item_movie.view.*


class ListAdapter(private val context: Context, private val type: Type) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private lateinit var data: List<Item>
    private lateinit var movieData: List<Movie>

    fun setData(mData: List<Item>) {
        data = mData
    }

    fun setMovieData(mData: List<Movie>) {
        movieData = mData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return if (::movieData.isInitialized) movieData.size else data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::movieData.isInitialized) {
            holder.bindItem(movieData[position])
        } else {
            holder.bindItem(data[position])
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(movie: Movie) {
            itemView.run {
                movie.also {
                    it.posterPath?.run {
                        movie_poster_layout.visibility = View.GONE
                        loading_movie_poster.visibility = View.VISIBLE
                        loading_movie_poster.startShimmerAnimation()

                        val url = getImageUrl(this)
                        Ion.with(context)
                            .load(url)
                            .asBitmap()
                            .setCallback { e, result ->
                                loading_movie_poster.stopShimmerAnimation()
                                if (e == null) {
                                    movie_poster.setImageBitmap(result)
                                    loading_movie_poster.visibility = View.GONE
                                    movie_poster_layout.visibility = View.VISIBLE
                                }
                            }
                    }
                    movie_title.text = it.title
                    movie_description.text = it.overview
                    movie_release_date.text = it.releaseDate.toFormattedDate()
                }
                setOnClickListener {
                    context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(DetailActivity.ARG_ID, movie.id)
                            .putExtra(DetailActivity.ARG_TYPE, type.ordinal)
                    )
                }
            }
        }

        fun bindItem(item: Item) {
            itemView.run {
                item.also {
                    movie_poster.setImagePoster(it.posterPath)
                    movie_title.text = it.title
                    movie_description.text = it.overview
                    movie_release_date.text = it.releaseDate.toFormattedDate()
                }
                setOnClickListener {
                    context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(DetailActivity.ARG_ID, item.id)
                            .putExtra(DetailActivity.ARG_TYPE, type.ordinal)
                    )
                }
            }
        }
    }
}