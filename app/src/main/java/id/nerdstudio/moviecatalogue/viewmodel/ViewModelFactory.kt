package id.nerdstudio.moviecatalogue.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.nerdstudio.moviecatalogue.api.ApiLoader
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository
import id.nerdstudio.moviecatalogue.di.Injection
import id.nerdstudio.moviecatalogue.ui.detail.DetailViewModel
import id.nerdstudio.moviecatalogue.ui.movie.FavoriteMovieViewModel
import id.nerdstudio.moviecatalogue.ui.main.MainViewModel
import id.nerdstudio.moviecatalogue.ui.movie.MovieViewModel
import id.nerdstudio.moviecatalogue.ui.tv.FavoriteTvShowViewModel
import id.nerdstudio.moviecatalogue.ui.tv.TvShowViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val catalogueRepository: CatalogueRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(catalogueRepository) as T
            modelClass.isAssignableFrom(MovieViewModel::class.java) ->
                MovieViewModel(catalogueRepository) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) ->
                TvShowViewModel(catalogueRepository) as T
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) ->
                FavoriteMovieViewModel(catalogueRepository) as T
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) ->
                FavoriteTvShowViewModel(catalogueRepository) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(catalogueRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(loader: ApiLoader, application: Application): ViewModelFactory? {
            synchronized(ViewModelFactory::class.java) {
                if (INSTANCE == null) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(loader, application))
                }
                return INSTANCE
            }
        }
    }

}