package id.nerdstudio.moviecatalogue.ui.content

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.util.setImagePoster
import id.nerdstudio.moviecatalogue.util.toFormattedDate
import kotlinx.android.synthetic.main.item_movie.view.*
import id.nerdstudio.moviecatalogue.ui.detail.DetailActivity
import android.content.Intent
import id.nerdstudio.moviecatalogue.data.Type


class ListAdapter(private val context: Context, private var data: List<Item>, private val type: Type) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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