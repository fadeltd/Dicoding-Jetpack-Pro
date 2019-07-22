package id.nerdstudio.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.entity.Item
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository

class MovieViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {

//    fun getMoviesApi(): LiveData<Triple<Boolean, List<Movie>, String>> {
//        return catalogueRepository.getAllMoviesApi()
//    }

    fun getMoviesRemote(): LiveData<List<Movie>> {
        return catalogueRepository.getAllMoviesRemote()
    }

    fun getMovies(): LiveData<List<Item>> {
        return catalogueRepository.getAllMovies()
    }
}