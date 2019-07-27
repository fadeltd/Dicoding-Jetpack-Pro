package id.nerdstudio.moviecatalogue.ui.detail.similar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koushikdutta.ion.Ion
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.config.AppConfig.PosterType.W185
import id.nerdstudio.moviecatalogue.config.AppConfig.getImageUrl
import id.nerdstudio.moviecatalogue.data.entity.Catalogue
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.ui.detail.DetailActivity
import id.nerdstudio.moviecatalogue.ui.detail.DetailActivity.Companion.ARG_ID
import id.nerdstudio.moviecatalogue.ui.detail.DetailActivity.Companion.ARG_TYPE
import kotlinx.android.synthetic.main.item_movie_similar.view.*

class SimilarAdapter(
    private val context: Context,
    private val data: List<Catalogue>
) : RecyclerView.Adapter<SimilarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie_similar, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(catalogue: Catalogue) {
            itemView.run {
                catalogue.also {
                    it.posterPath?.run {
                        val url = getImageUrl(this, W185)
                        Ion.with(context)
                            .load(url)
                            .asBitmap()
                            .setCallback { e, result ->
                                if (e == null) {
                                    similar_movie_poster.setImageBitmap(result)
                                }
                            }
                    }
                    similar_movie_title.text =
                        when (it) {
                            is Movie -> it.title
                            is TvShow -> it.name
                            else -> ""
                        }
                    movie_rating.text = it.voteAverage.toString()
                }
                setOnClickListener {
                    context.startActivity(
                        Intent(context, DetailActivity::class.java)
                            .putExtra(ARG_ID, catalogue.id)
                            .putExtra(ARG_TYPE, Type.MOVIE)
                    )
                }
            }
        }
    }
}