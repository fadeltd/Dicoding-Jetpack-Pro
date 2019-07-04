package id.nerdstudio.moviecatalogue.ui.tv

import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.util.Dummy.dummyTvShows

class TvShowViewModel : ViewModel() {
    fun getTvShows(): List<Item> {
        return dummyTvShows()
    }
}