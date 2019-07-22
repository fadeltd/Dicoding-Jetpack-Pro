package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import id.nerdstudio.moviecatalogue.data.entity.Cast
import id.nerdstudio.moviecatalogue.data.entity.Crew
import id.nerdstudio.moviecatalogue.data.entity.Movie

interface MovieDataSource {

    fun getAllMoviesRemote(): LiveData<List<Movie>>

    fun getMovieCast(id: Long): LiveData<List<Cast>>

    fun getMovieCrew(id: Long): LiveData<List<Crew>>

    fun getMovieSimilar(id: Long): LiveData<List<Movie>>

    fun getMovieDetail(id: Long): LiveData<Movie>
}