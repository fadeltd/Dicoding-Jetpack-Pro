package id.nerdstudio.moviecatalogue.ui.detail.cast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koushikdutta.ion.Ion
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.config.AppConfig.PosterType.W185
import id.nerdstudio.moviecatalogue.config.AppConfig.getImageUrl
import id.nerdstudio.moviecatalogue.data.entity.Cast
import kotlinx.android.synthetic.main.item_cast.view.*

class CastAdapter(
    private val context: Context,
    private val data: List<Cast>
) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(cast: Cast) {
            itemView.run {
                cast.also {
                    it.profilePath?.run {
                        val url = getImageUrl(this, W185)
                        Ion.with(context)
                            .load(url)
                            .asBitmap()
                            .setCallback { e, result ->
                                if (e == null) {
                                    cast_avatar.setImageBitmap(result)
                                }
                            }
                    }
                    cast_actor.text = it.name
                    cast_character.text = it.character
                }
            }
        }
    }
}