package id.nerdstudio.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository

class ItemRepository(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository
) : ItemDataSource {
    override fun getAllMovies(): LiveData<List<Item>> {
        val movieResults = MutableLiveData<List<Item>>()
        remoteRepository.getAllItems(Type.MOVIE, { movies ->
            var movieList = listOf<Item>()
            for (i in movies.indices) {
                val movie = movies[i]
                movieList += movie
            }
            movieResults.postValue(movieList)
        })
        return movieResults
    }

    override fun getAllTvShows(): LiveData<List<Item>> {
        val tvShowResults = MutableLiveData<List<Item>>()
        remoteRepository.getAllItems(Type.TV_SHOW, { tvShows ->
            var tvShowList = listOf<Item>()
            for (i in tvShows.indices) {
                val tvShow = tvShows[i]
                tvShowList += tvShow
            }
            tvShowResults.postValue(tvShowList)
        })
        return tvShowResults
    }

//    override fun getFavoriteMovies(): LiveData<List<Item>> {
//        val movieResults = MutableLiveData<List<Item>>()
//        remoteRepository.getFavoriteItems(Type.MOVIE, { movies ->
//            var movieList = listOf<Item>()
//            for (i in movies.indices) {
//                val movie = movies[i]
//                movieList += movie
//            }
//            movieResults.postValue(movieList)
//        })
//        return movieResults
//    }
//
//    override fun getFavoriteTvShows(): LiveData<List<Item>> {
//        val tvShowResults = MutableLiveData<List<Item>>()
//        remoteRepository.getAllItems(Type.TV_SHOW, { tvShows ->
//            var tvShowList = listOf<Item>()
//            for (i in tvShows.indices) {
//                val tvShow = tvShows[i]
//                tvShowList += tvShow
//            }
//            tvShowResults.postValue(tvShowList)
//        })
//        return tvShowResults
//    }

    override fun getContent(id: Long, type: Type): LiveData<Item> {
        val itemResult = MutableLiveData<Item>()
        remoteRepository.getAllItems(type, { items ->
            for (i in items.indices) {
                val item = items[i]
                val itemId = item.id
                if (id == itemId) {
                    itemResult.postValue(item)
                    break
                }
            }
        })
        return itemResult
    }

    companion object {
        @Volatile
        private var INSTANCE: ItemRepository? = null

        fun getInstance(
            localRepository: LocalRepository,
            remoteData: RemoteRepository
        ): ItemRepository {
            if (INSTANCE == null) {
                synchronized(ItemRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE =
                            ItemRepository(localRepository, remoteData)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}