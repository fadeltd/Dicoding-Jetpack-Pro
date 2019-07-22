package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import id.nerdstudio.moviecatalogue.data.entity.Item
import id.nerdstudio.moviecatalogue.data.entity.Type

interface ItemDataSource {

    fun getAllMovies(): LiveData<List<Item>>

    fun getAllTvShows(): LiveData<List<Item>>

    fun getContent(id: Long, type: Type): LiveData<Item>
}
