package id.nerdstudio.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.Cast
import id.nerdstudio.moviecatalogue.data.Crew
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Movie
import id.nerdstudio.moviecatalogue.data.source.ItemRepository

class MovieViewModel(private val itemRepository: ItemRepository) : ViewModel() {

//    fun getMoviesApi(): LiveData<Triple<Boolean, List<Movie>, String>> {
//        return itemRepository.getAllMoviesApi()
//    }

    fun getMoviesRemote(): LiveData<List<Movie>> {
        return itemRepository.getAllMoviesRemote()
    }

    fun getMovies(): LiveData<List<Item>> {
        return itemRepository.getAllMovies()
    }
}