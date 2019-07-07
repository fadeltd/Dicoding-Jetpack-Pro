package id.nerdstudio.moviecatalogue.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.nerdstudio.moviecatalogue.data.source.ItemRepository
import id.nerdstudio.moviecatalogue.di.Injection
import id.nerdstudio.moviecatalogue.ui.detail.DetailViewModel
import id.nerdstudio.moviecatalogue.ui.movie.MovieViewModel
import id.nerdstudio.moviecatalogue.ui.tv.TvShowViewModel

class ViewModelFactory private constructor(private val itemRepository: ItemRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(itemRepository) as T
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(itemRepository) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(itemRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory? {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = ViewModelFactory(Injection.provideRepository(application))
                    }
                }
            }
            return INSTANCE
        }
    }

}