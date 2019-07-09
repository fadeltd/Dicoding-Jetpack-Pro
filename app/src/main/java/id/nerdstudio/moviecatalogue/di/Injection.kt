package id.nerdstudio.moviecatalogue.di


import android.app.Application
import id.nerdstudio.moviecatalogue.api.ApiLoader
import id.nerdstudio.moviecatalogue.data.source.ItemRepository
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository

import id.nerdstudio.moviecatalogue.util.JsonUtils

object Injection {
    fun provideRepository(loader: ApiLoader, application: Application): ItemRepository {

        val localRepository = LocalRepository.getInstance(JsonUtils(application))
        val remoteRepository = RemoteRepository(loader)

        return ItemRepository.getInstance(localRepository, remoteRepository)
    }
}