package id.nerdstudio.moviecatalogue.di

import android.app.Application
import id.nerdstudio.moviecatalogue.api.ApiLoader
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository
import id.nerdstudio.moviecatalogue.data.source.favorite.FavoriteRepository
import id.nerdstudio.moviecatalogue.data.source.favorite.room.FavoriteDatabase
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository
import id.nerdstudio.moviecatalogue.util.AppExecutors
import id.nerdstudio.moviecatalogue.util.JsonUtils

object Injection {
    fun provideRepository(loader: ApiLoader, application: Application): CatalogueRepository {
        val localRepository = LocalRepository.getInstance(JsonUtils(application))
        val remoteRepository = RemoteRepository.getInstance(loader)
        val favoriteRepository = FavoriteRepository.getInstance(
            FavoriteDatabase.getInstance(application).favoriteDao()
        )
        val appExecutors = AppExecutors()

        return CatalogueRepository.getInstance(
            localRepository,
            remoteRepository,
            favoriteRepository,
            appExecutors
        )
    }
}