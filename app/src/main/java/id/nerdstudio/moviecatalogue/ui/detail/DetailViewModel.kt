package id.nerdstudio.moviecatalogue.ui.detail

import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.util.Dummy.dummyMovies
import id.nerdstudio.moviecatalogue.util.Dummy.dummyTvShows

class DetailViewModel : ViewModel() {
    var type: Type = Type.MOVIE
    var id: Long? = null

    fun getItem(): Item? {
        val list = if (type == Type.MOVIE) dummyMovies() else dummyTvShows()
        for (i in 0 until list.size) {
            val item = list[i]
            if (item.id == id) {
                return item
            }
        }
        return null
    }
}