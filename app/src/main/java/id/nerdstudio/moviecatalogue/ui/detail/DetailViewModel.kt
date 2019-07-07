package id.nerdstudio.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.data.source.ItemRepository

class DetailViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    var id: Long = 0
    var type: Type = Type.MOVIE

    fun getItem(): LiveData<Item>? {
        return itemRepository.getContent(id, type)
    }
}