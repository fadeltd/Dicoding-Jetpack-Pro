package id.nerdstudio.moviecatalogue.ui.movie

import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.util.Dummy.dummyMovies

class MovieViewModel : ViewModel() {
    fun getMovies(): List<Item> {
        return dummyMovies()
    }
}