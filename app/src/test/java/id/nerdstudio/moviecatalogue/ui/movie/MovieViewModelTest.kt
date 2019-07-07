package id.nerdstudio.moviecatalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.source.ItemRepository
import id.nerdstudio.moviecatalogue.util.Dummy
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieViewModel
    private val itemRepository = mock(ItemRepository::class.java)

    @Before
    fun setUp() {
        viewModel = MovieViewModel(itemRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getMovies() {
        val mockMovies = MutableLiveData<List<Item>>()
        mockMovies.value = Dummy.dummyMovies()

        `when`(itemRepository.getAllMovies()).thenReturn(mockMovies)

        val observer: Observer<List<Item>> = mock()
        viewModel.getMovies().observeForever(observer)

        assertNotNull(mockMovies.value)
        assertEquals(19, mockMovies.value?.size)

        verify(itemRepository).getAllMovies()
    }
}
