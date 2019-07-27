package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import id.nerdstudio.moviecatalogue.data.entity.Cast
import id.nerdstudio.moviecatalogue.data.entity.Crew
import id.nerdstudio.moviecatalogue.data.entity.TvShow

interface TvShowDataSource {

    fun getAllTvShowsRemote(): LiveData<List<TvShow>>

    fun getTvShowCast(id: Long): LiveData<List<Cast>>

    fun getTvShowCrew(id: Long): LiveData<List<Crew>>

    fun getTvShowSimilar(id: Long): LiveData<List<TvShow>>

    fun getTvShowDetail(id: Long): LiveData<TvShow>
}