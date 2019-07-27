package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow

interface CatalogueDataSource {

//    fun <T> getAllItems(): LiveData<List<T>>
//    fun <T> getContent(id: Long): LiveData<T>

    fun getAllMovies(): LiveData<List<Movie>>

    fun getAllTvShows(): LiveData<List<TvShow>>

    fun getMovieContent(id: Long): LiveData<Movie>

    fun getTvShowContent(id: Long): LiveData<TvShow>
}
