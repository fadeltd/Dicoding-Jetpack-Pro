package id.nerdstudio.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.entity.*
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository

class DetailViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    var id: Long = 0
    var type: Type = Type.MOVIE

    fun getItem(): LiveData<Item>? {
        return catalogueRepository.getContent(id, type)
    }

    fun getMovieDetail(): LiveData<Movie>{
        return catalogueRepository.getMovieDetail(id)
    }

    fun getMovieCast(): LiveData<List<Cast>>{
        return catalogueRepository.getMovieCast(id)
    }

    fun getMovieCrew() : LiveData<List<Crew>> {
        return catalogueRepository.getMovieCrew(id)
    }

    fun getSimilarMovies() : LiveData<List<Movie>> {
        return catalogueRepository.getMovieSimilar(id)
    }

    fun addToFavorite(movie: Movie) {
         catalogueRepository.insertFavoriteMovie(movie)
    }

    fun addToFavorite(tvShow: TvShow) {
        catalogueRepository.insertFavoriteTvShow(tvShow)
    }
}