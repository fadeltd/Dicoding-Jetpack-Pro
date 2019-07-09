package id.nerdstudio.moviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.source.ItemRepository

class TvShowViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    fun getTvShows(): LiveData<List<Item>> {
        return itemRepository.getAllTvShows()
    }
}