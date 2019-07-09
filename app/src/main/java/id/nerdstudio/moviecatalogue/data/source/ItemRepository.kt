package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.nerdstudio.moviecatalogue.data.Movie
import id.nerdstudio.moviecatalogue.data.Cast
import id.nerdstudio.moviecatalogue.data.Crew
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository

class ItemRepository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ItemDataSource, MovieDataSource {

    /**
     * Remote repository
     */
    override fun getAllMoviesRemote(): LiveData<List<Movie>> {
        val movieResults = MutableLiveData<List<Movie>>()
        remoteRepository.getAllShows(Type.MOVIE, {
            // On Complete
        }, { movies ->
            var movieList = listOf<Movie>()
            for (i in movies.indices) {
                val movie = movies[i]
                movieList += movie as Movie
            }
            movieResults.postValue(movieList)
        }, {
            // On Fail
        })
        return movieResults
    }

    override fun getMovieCast(id: Long): LiveData<List<Cast>> {
        val result = MutableLiveData<List<Cast>>()
        remoteRepository.getCredits(Type.MOVIE, id, {
            // On Complete
        }, { castList, _ ->
            var list = listOf<Cast>()
            for (i in castList.indices) {
                list += castList[i]
            }
            result.postValue(list)
        }, {
            // On Fail
        })
        return result
    }

    override fun getMovieCrew(id: Long): LiveData<List<Crew>> {
        val result = MutableLiveData<List<Crew>>()
        remoteRepository.getCredits(Type.MOVIE, id, {
            // On Complete
        }, { _, crewList ->
            var list = listOf<Crew>()
            for (i in crewList.indices) {
                list += crewList[i]
            }
            result.postValue(list)
        }, {
            // On Fail
        })
        return result
    }

    override fun getMovieSimilar(id: Long): LiveData<List<Movie>> {
        val result = MutableLiveData<List<Movie>>()
        remoteRepository.getSimilar(Type.MOVIE, id, {
            // On Complete
        }, { movies ->
            var list = listOf<Movie>()
            for (i in movies.indices) {
                list += movies[i] as Movie
            }
            result.postValue(list)
        }, {
            // On Fail
        })
        return result
    }

    override fun getMovieDetail(id: Long): LiveData<Movie> {
        val result = MutableLiveData<Movie>()
        remoteRepository.getMovieDetail(id, {
            // On Complete
        }, { movie ->
            result.postValue(movie)
        }, {
            // On Fail
        })
        return result
    }

    /**
     * Local repository
     */
    override fun getAllMovies(): LiveData<List<Item>> {
        val movieResults = MutableLiveData<List<Item>>()
        localRepository.getAllItems(Type.MOVIE) { movies ->
            var movieList = listOf<Item>()
            for (i in movies.indices) {
                val movie = movies[i]
                movieList += movie
            }
            movieResults.postValue(movieList)
        }
        return movieResults
    }

    override fun getAllTvShows(): LiveData<List<Item>> {
        val tvShowResults = MutableLiveData<List<Item>>()
        localRepository.getAllItems(Type.TV_SHOW) { tvShows ->
            var tvShowList = listOf<Item>()
            for (i in tvShows.indices) {
                val tvShow = tvShows[i]
                tvShowList += tvShow
            }
            tvShowResults.postValue(tvShowList)
        }
        return tvShowResults
    }

    override fun getContent(id: Long, type: Type): LiveData<Item> {
        val itemResult = MutableLiveData<Item>()
        localRepository.getAllItems(type) { items ->
            for (i in items.indices) {
                val item = items[i]
                val itemId = item.id
                if (id == itemId) {
                    itemResult.postValue(item)
                    break
                }
            }
        }
        return itemResult
    }

    companion object {
        @Volatile
        private var INSTANCE: ItemRepository? = null

        fun getInstance(
            localRepository: LocalRepository,
            remoteRepository: RemoteRepository
        ): ItemRepository {
            if (INSTANCE == null) {
                synchronized(ItemRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ItemRepository(localRepository, remoteRepository)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}