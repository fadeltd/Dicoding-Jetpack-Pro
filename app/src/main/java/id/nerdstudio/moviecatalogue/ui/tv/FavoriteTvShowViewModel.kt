package id.nerdstudio.moviecatalogue.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository

class FavoriteTvShowViewModel(private val catalogueRepository: CatalogueRepository) : ViewModel() {
    fun getFavoriteTvShows(): LiveData<PagedList<TvShow>> = catalogueRepository.getFavoriteTvShows()
}