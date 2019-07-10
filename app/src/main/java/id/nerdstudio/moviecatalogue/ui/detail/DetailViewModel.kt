package id.nerdstudio.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.*
import id.nerdstudio.moviecatalogue.data.source.ItemRepository

class DetailViewModel(private val itemRepository: ItemRepository) : ViewModel() {
    var id: Long = 0
    var type: Type = Type.MOVIE

    fun getItem(): LiveData<Item>? {
        return itemRepository.getContent(id, type)
    }

    fun getMovieDetail(): LiveData<Movie>{
        return itemRepository.getMovieDetail(id)
    }

    fun getMovieCast(): LiveData<List<Cast>>{
        return itemRepository.getMovieCast(id)
    }

    fun getMovieCrew() : LiveData<List<Crew>> {
        return itemRepository.getMovieCrew(id)
    }

    fun getSimilarMovies() : LiveData<List<Movie>> {
        return itemRepository.getMovieSimilar(id)
    }
}