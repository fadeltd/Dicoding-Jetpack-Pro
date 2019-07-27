package id.nerdstudio.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.entity.*
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository

class DetailViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    var id: Long = 0
    var type: Type = Type.MOVIE

    fun getMovieContent(): LiveData<Movie> {
        return catalogueRepository.getMovieContent(id)
    }

    fun getTvShowContent(): LiveData<TvShow> {
        return catalogueRepository.getTvShowContent(id)
    }

    fun getDetail(type: Type): LiveData<*> {
        return if (type == Type.MOVIE)
            catalogueRepository.getMovieDetail(id)
        else
            catalogueRepository.getTvShowDetail(id)
    }

    fun getCast(type: Type): LiveData<List<Cast>> {
        return if (type == Type.MOVIE)
            catalogueRepository.getMovieCast(id)
        else
            catalogueRepository.getTvShowCast(id)
    }

    fun getCrew(type: Type): LiveData<List<Crew>> {
        return if (type == Type.MOVIE)
            catalogueRepository.getMovieCrew(id)
        else
            catalogueRepository.getTvShowCrew(id)
    }

//    fun getSimilar(): LiveData<List<Catalogue>> {
//        return if (type == Type.MOVIE)
//            catalogueRepository.getMovieSimilar(id)
//        else
//            catalogueRepository.getTvShowSimilar(id)
//    }

    fun getSimilarMovies(): LiveData<List<Movie>> {
        return catalogueRepository.getMovieSimilar(id)
    }

    fun getSimilarTvShows(): LiveData<List<TvShow>> {
        return catalogueRepository.getTvShowSimilar(id)
    }

    fun removeFavoriteMovie(movie: Movie) {
        catalogueRepository.deleteFavoriteMovie(movie)
    }

    fun removeFavoriteTvShow(tvShow: TvShow) {
        catalogueRepository.deleteFavoriteTvShow(tvShow)
    }

    fun isFavoriteMovie(id: Long): Boolean {
        return catalogueRepository.isFavoriteMovie(id)
    }

    fun isFavoriteTvShow(id: Long): Boolean {
        return catalogueRepository.isFavoriteTvShow(id)
    }

    fun addToFavorite(movie: Movie) {
        catalogueRepository.insertFavoriteMovie(movie)
    }

    fun addToFavorite(tvShow: TvShow) {
        catalogueRepository.insertFavoriteTvShow(tvShow)
    }
}