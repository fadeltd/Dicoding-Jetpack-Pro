package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import id.nerdstudio.moviecatalogue.data.Cast
import id.nerdstudio.moviecatalogue.data.Crew
import id.nerdstudio.moviecatalogue.data.TvShow

interface TvShowDataSource {
    fun getAllTvShowRemote(): LiveData<List<TvShow>>

    fun getTvShowCast(id: Long): LiveData<List<Cast>>

    fun getTvShowCrew(id: Long): LiveData<List<Crew>>

    fun getTvShowSimilar(id: Long): LiveData<List<TvShow>>

    fun getTvShowDetail(id: Long): LiveData<TvShow>
}