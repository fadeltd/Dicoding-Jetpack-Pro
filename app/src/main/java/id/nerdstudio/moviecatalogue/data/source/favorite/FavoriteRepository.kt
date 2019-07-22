package id.nerdstudio.moviecatalogue.data.source.favorite

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.source.favorite.room.FavoriteDao

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun getFavoriteMoviesPaged(): DataSource.Factory<Int, Movie> = favoriteDao.favoriteMoviesPaged

    fun getFavoriteTvShowsPaged(): DataSource.Factory<Int, TvShow> = favoriteDao.favoriteTvShowsPaged

    fun isFavoriteMovie(id: Long): LiveData<Movie> = favoriteDao.isFavoriteMovie(id)

    fun isFavoriteTvShow(id: Long): LiveData<TvShow> = favoriteDao.isFavoriteTvShow(id)

    fun insertFavoriteMovie(movie: Movie) {
        favoriteDao.insertFavorite(movie)
    }

    fun insertFavoriteTvShow(tvShow: TvShow) {
        favoriteDao.insertFavorite(tvShow)
    }

    fun deleteFavoriteMovie(movie: Movie) {
        favoriteDao.deleteFavorite(movie)
    }

    fun deleteFavoriteTvShow(tvShow: TvShow) {
        favoriteDao.deleteFavorite(tvShow)
    }

    companion object {
        private var INSTANCE: FavoriteRepository? = null

        fun getInstance(academyDao: FavoriteDao): FavoriteRepository {
            if (INSTANCE == null) {
                INSTANCE = FavoriteRepository(academyDao)
            }
            return INSTANCE as FavoriteRepository
        }

    }
}