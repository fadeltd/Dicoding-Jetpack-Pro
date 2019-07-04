package id.nerdstudio.moviecatalogue.ui.movie

import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        viewModel = MovieViewModel()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getMovies() {
        val list = viewModel.getMovies()
        assertNotNull(list)
        assertEquals(19, list.size)
    }
}