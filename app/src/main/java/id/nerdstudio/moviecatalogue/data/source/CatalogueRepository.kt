package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import id.nerdstudio.moviecatalogue.data.entity.*
import id.nerdstudio.moviecatalogue.data.source.favorite.FavoriteRepository
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository
import id.nerdstudio.moviecatalogue.util.AppExecutors
import id.nerdstudio.moviecatalogue.vo.Resource
import androidx.paging.LivePagedListBuilder


class CatalogueRepository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val favoriteRepository: FavoriteRepository,
    private val appExecutors: AppExecutors
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
                movieList = movieList + movie as Movie
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
                list = list + castList[i]
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
                list = list + crewList[i]
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
                list = list + movies[i] as Movie
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
                movieList = movieList + movie
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
                tvShowList = tvShowList + tvShow
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

    //region Favorites
    fun insertFavoriteMovie(movie: Movie) {
        val runnable = { favoriteRepository.insertFavoriteMovie(movie) }
        appExecutors.diskIO().execute(runnable)
    }

    fun insertFavoriteTvShow(tvShow: TvShow) {
        val runnable = { favoriteRepository.insertFavoriteTvShow(tvShow) }
        appExecutors.diskIO().execute(runnable)
    }

    fun isFavoriteMovie(id: Long): Boolean {
        return favoriteRepository.isFavoriteMovie(id)
    }

    fun isFavoriteTvShow(id: Long): Boolean {
        return favoriteRepository.isFavoriteTvShow(id)
    }

    fun getFavoriteMovies(): LiveData<PagedList<Movie>> {
        return LivePagedListBuilder(favoriteRepository.getFavoriteMoviesPaged(), 20).build()
    }

    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>> {
        return LivePagedListBuilder(favoriteRepository.getFavoriteTvShowsPaged(), 20).build()
    }
    //endregion

    companion object {
        @Volatile
        private var INSTANCE: CatalogueRepository? = null

        fun getInstance(
            localRepository: LocalRepository,
            remoteRepository: RemoteRepository,
            favoriteRepository: FavoriteRepository,
            appExecutors: AppExecutors
        ): CatalogueRepository {
            synchronized(CatalogueRepository::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = CatalogueRepository(
                        localRepository,
                        remoteRepository,
                        favoriteRepository,
                        appExecutors
                    )
                }
                return INSTANCE as CatalogueRepository
            }
        }
    }
}