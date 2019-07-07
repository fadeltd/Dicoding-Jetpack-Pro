package id.nerdstudio.moviecatalogue.ui.tv

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

class TvShowViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TvShowViewModel
    private val itemRepository = mock(ItemRepository::class.java)

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(itemRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTvShows() {
        val mockTvShows = MutableLiveData<List<Item>>()
        mockTvShows.value = Dummy.dummyTvShows()

        `when`(itemRepository.getAllTvShows()).thenReturn(mockTvShows)

        val observer: Observer<List<Item>> = mock()
        viewModel.getTvShows().observeForever(observer)

        assertNotNull(mockTvShows.value)
        assertEquals(20, mockTvShows.value?.size)

        verify(itemRepository).getAllTvShows()
    }
}
