package id.nerdstudio.moviecatalogue.data.source.favorite.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow

@Dao
interface FavoriteDao {

    @get:WorkerThread
    @get:Query("SELECT * FROM favorite_movies")
    val favoriteMoviesPaged: DataSource.Factory<Int, Movie>

    @get:WorkerThread
    @get:Query("SELECT * FROM favorite_tv_shows")
    val favoriteTvShowsPaged: DataSource.Factory<Int, TvShow>

    @Query("SELECT COUNT(id) FROM favorite_movies WHERE id = :id")
    fun countFavoriteMovie(id: Long): Int

    @Query("SELECT COUNT(id) FROM favorite_tv_shows WHERE id = :id")
    fun countFavoriteTvShow(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(movie: Movie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(tvShow: TvShow): Long

//    @Update(onConflict = OnConflictStrategy.FAIL)
//    fun updateFavoriteMovie(movie: Movie): Int
//
//    @Update(onConflict = OnConflictStrategy.FAIL)
//    fun updateFavoriteTvShow(tvShow: TvShow): Int

    @Delete
    fun deleteFavorite(movie: Movie)

    @Delete
    fun deleteFavorite(tvShow: TvShow)
}