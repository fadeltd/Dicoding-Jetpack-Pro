package id.nerdstudio.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository

class MovieViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {

    fun getMoviesRemote(): LiveData<List<Movie>> {
        return catalogueRepository.getAllMoviesRemote()
    }

    fun getMovies(): LiveData<List<Movie>> {
        return catalogueRepository.getAllMovies()
    }
}