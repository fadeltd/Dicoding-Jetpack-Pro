package id.nerdstudio.moviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository

class TvShowViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {

    fun getTvShowsRemote(): LiveData<List<TvShow>> {
        return catalogueRepository.getAllTvShowsRemote()
    }

    fun getTvShows(): LiveData<List<TvShow>> {
        return catalogueRepository.getAllTvShows()
    }
}