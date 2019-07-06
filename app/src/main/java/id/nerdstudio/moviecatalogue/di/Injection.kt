package id.nerdstudio.moviecatalogue.di


import android.app.Application
import id.nerdstudio.moviecatalogue.data.source.ItemRepository
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository

import id.nerdstudio.moviecatalogue.util.JsonUtils

object Injection {
    fun provideRepository(application: Application): ItemRepository {

        val localRepository = LocalRepository()
        val remoteRepository = RemoteRepository.getInstance(JsonUtils(application))

        return ItemRepository.getInstance(localRepository, remoteRepository)
    }
}