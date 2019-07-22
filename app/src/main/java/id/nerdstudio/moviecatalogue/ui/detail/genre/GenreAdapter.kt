package id.nerdstudio.moviecatalogue.ui.detail.genre

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.entity.Genre
import kotlinx.android.synthetic.main.item_genre.view.*

class GenreAdapter(
    private val context: Context,
    private val data: List<Genre>
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_genre, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(genre: Genre) {
            itemView.run {
                genre.also {
                    genre_text.text = it.name
                }
            }
        }
    }
}