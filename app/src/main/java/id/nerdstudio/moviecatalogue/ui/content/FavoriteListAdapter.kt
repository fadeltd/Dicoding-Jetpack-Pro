package id.nerdstudio.moviecatalogue.ui.content

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.koushikdutta.ion.Ion
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.config.AppConfig
import id.nerdstudio.moviecatalogue.data.entity.Catalogue
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.util.setImagePoster
import id.nerdstudio.moviecatalogue.util.toFormattedDate
import id.nerdstudio.moviecatalogue.ui.detail.DetailActivity
import id.nerdstudio.moviecatalogue.ui.main.PageType
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteListAdapter(
    private val context: Context,
    private val type: Type,
    private val pageType: PageType,
    diffCallback: DiffUtil.ItemCallback<Catalogue>
) : PagedListAdapter<Catalogue, FavoriteListAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(item: Catalogue?) {
            itemView.run {
                item?.also {
                    it.posterPath?.run {
                        movie_poster_layout.visibility = View.GONE
                        loading_movie_poster.visibility = View.VISIBLE
                        loading_movie_poster.startShimmerAnimation()
                        if (!this.endsWith("jpg")) {
                            movie_poster.setImagePoster(this)
                            loading_movie_poster.stopShimmerAnimation()
                            loading_movie_poster.visibility = View.GONE
                            movie_poster_layout.visibility = View.VISIBLE
                        } else {
                            val url = AppConfig.getImageUrl(this)
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
                    }
                    movie_title.text =
                        when (it) {
                            is Movie -> it.title
                            is TvShow -> it.name
                            else -> ""
                        }
                    movie_description.text = it.overview
                    movie_release_date.text =
                        when (it) {
                            is Movie -> it.releaseDate.toFormattedDate()
                            is TvShow -> it.firstAirDate.toFormattedDate()
                            else -> ""
                        }
                }
                setOnClickListener {
                    context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(DetailActivity.ARG_ID, item?.id)
                            .putExtra(DetailActivity.ARG_TYPE, type)
                            .putExtra(DetailActivity.ARG_PAGE_TYPE, pageType)
                    )
                }
            }
        }
    }
}