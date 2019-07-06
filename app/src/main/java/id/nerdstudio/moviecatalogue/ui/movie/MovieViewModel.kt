package id.nerdstudio.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.source.ItemRepository
import id.nerdstudio.moviecatalogue.util.Dummy.dummyMovies

class MovieViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    fun getMovies(): LiveData<List<Item>> {
        return itemRepository.getAllMovies()
    }
}