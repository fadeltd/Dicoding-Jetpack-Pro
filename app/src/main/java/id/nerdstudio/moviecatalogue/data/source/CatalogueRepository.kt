package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import id.nerdstudio.moviecatalogue.data.entity.*
import id.nerdstudio.moviecatalogue.data.source.favorite.FavoriteRepository
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository
import id.nerdstudio.moviecatalogue.util.AppExecutors

class CatalogueRepository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
    private val favoriteRepository: FavoriteRepository,
    private val appExecutors: AppExecutors
) : CatalogueDataSource, MovieDataSource, TvShowDataSource {

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
                movieList = movieList + (movie as Movie)
            }
            movieResults.postValue(movieList)
        }, {
            // On Fail
        })
        return movieResults
    }

    override fun getAllTvShowsRemote(): LiveData<List<TvShow>> {
        val movieResults = MutableLiveData<List<TvShow>>()
        remoteRepository.getAllShows(Type.TV_SHOW, {
            // On Complete
        }, { shows ->
            var list = listOf<TvShow>()
            for (i in shows.indices) {
                val movie = shows[i]
                list = list + (movie as TvShow)
            }
            movieResults.postValue(list)
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

    override fun getTvShowCast(id: Long): LiveData<List<Cast>> {
        val result = MutableLiveData<List<Cast>>()
        remoteRepository.getCredits(Type.TV_SHOW, id, {
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


    override fun getTvShowCrew(id: Long): LiveData<List<Crew>> {
        val result = MutableLiveData<List<Crew>>()
        remoteRepository.getCredits(Type.TV_SHOW, id, {
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


    override fun getTvShowSimilar(id: Long): LiveData<List<TvShow>> {
        val result = MutableLiveData<List<TvShow>>()
        remoteRepository.getSimilar(Type.TV_SHOW, id, {
            // On Complete
        }, { tvShows ->
            var list = listOf<TvShow>()
            for (i in tvShows.indices) {
                list = list + tvShows[i] as TvShow
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


    override fun getTvShowDetail(id: Long): LiveData<TvShow> {
        val result = MutableLiveData<TvShow>()
        remoteRepository.getTvShowDetail(id, {
            // On Complete
        }, { tvShow ->
            result.postValue(tvShow)
        }, {
            // On Fail
        })
        return result
    }

    /**
     * Local repository
     */
    override fun getAllMovies(): LiveData<List<Movie>> {
        val movieResults = MutableLiveData<List<Movie>>()
        localRepository.getAllMovies { movies ->
            var movieList = listOf<Movie>()
            for (i in movies.indices) {
                val movie = movies[i]
                movieList = movieList + movie
            }
            movieResults.postValue(movieList)
        }
        return movieResults
    }

    override fun getAllTvShows(): LiveData<List<TvShow>> {
        val tvShowResults = MutableLiveData<List<TvShow>>()
        localRepository.getAllTvShow { tvShows ->
            var tvShowList = listOf<TvShow>()
            for (i in tvShows.indices) {
                val tvShow = tvShows[i]
                tvShowList = tvShowList + tvShow
            }
            tvShowResults.postValue(tvShowList)
        }
        return tvShowResults
    }

    override fun getMovieContent(id: Long): LiveData<Movie> {
        val result = MutableLiveData<Movie>()
        localRepository.getAllMovies { movies ->
            for (i in movies.indices) {
                val movie = movies[i]
                val movieId = movie.id
                if (id == movieId) {
                    result.postValue(movie)
                    break
                }
            }
        }
        return result
    }

    override fun getTvShowContent(id: Long): LiveData<TvShow> {
        val result = MutableLiveData<TvShow>()
        localRepository.getAllTvShow { tvShows ->
            for (i in tvShows.indices) {
                val tvShow = tvShows[i]
                val tvShowId = tvShow.id
                if (id == tvShowId) {
                    result.postValue(tvShow)
                    break
                }
            }
        }
        return result
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

    fun deleteFavoriteMovie(movie: Movie) {
        val runnable = { favoriteRepository.deleteFavoriteMovie(movie) }
        appExecutors.diskIO().execute(runnable)
    }

    fun deleteFavoriteTvShow(tvShow: TvShow) {
        val runnable = { favoriteRepository.deleteFavoriteTvShow(tvShow) }
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