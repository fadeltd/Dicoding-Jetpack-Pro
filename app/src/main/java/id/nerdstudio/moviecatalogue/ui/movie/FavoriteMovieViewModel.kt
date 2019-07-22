package id.nerdstudio.moviecatalogue.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository

class FavoriteMovieViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getFavoriteMovies(): LiveData<PagedList<Movie>> = catalogueRepository.getFavoriteMovies()
}