package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type

interface ItemDataSource {

    fun getAllMovies(): LiveData<List<Item>>

    fun getAllTvShows(): LiveData<List<Item>>

//    fun getFavoriteMovies(): LiveData<List<Item>>
//
//    fun getFavoriteTvShows(): LiveData<List<Item>>

    fun getContent(id: Long, type: Type): LiveData<Item>
}
