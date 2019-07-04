package id.nerdstudio.moviecatalogue.ui.tv

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @Before
    fun setUp() {
        viewModel = TvShowViewModel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTvShows() {
        val list = viewModel.getTvShows()
        assertNotNull(list)
        assertEquals(20, list.size)
    }
}