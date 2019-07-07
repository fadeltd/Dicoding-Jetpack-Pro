package id.nerdstudio.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository
import id.nerdstudio.moviecatalogue.util.Dummy
import id.nerdstudio.moviecatalogue.util.getData
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ItemRepositoryTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val local: LocalRepository = mock()
    private val remote: RemoteRepository = mock()
    private val itemRepository = ItemRepository(local, remote)

    private val movies = Dummy.dummyMovies()
    private val movieId = movies[0].id
    private val tvShows = Dummy.dummyTvShows()
    private val tvShowId = tvShows[0].id

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun getAllMovies() {
        val type = Type.MOVIE
        whenever(remote.getAllItems(eq(type), any())).thenAnswer {
            val callback = it.getArgument<((movies: List<Item>) -> Unit)>(1)
            callback.invoke(movies)
        }
        val result = itemRepository.getAllMovies().value
        assertEquals(movies.size, result?.size)

        val onReceivedMock: (List<Item>) -> Unit = mock()
        remote.getAllItems(type, onReceivedMock)
        argumentCaptor<List<Item>>().apply {
            verify(onReceivedMock, times(1)).invoke(capture())
            assertEquals(movies, firstValue)
        }
    }

    @Test
    fun getAllTvShows() {
        val type = Type.TV_SHOW
        whenever(remote.getAllItems(eq(type), any())).thenAnswer {
            val callback = it.getArgument<((tvShows: List<Item>) -> Unit)>(1)
            callback.invoke(tvShows)
        }
        val result = itemRepository.getAllTvShows().value
        assertEquals(tvShows.size, result?.size)

        val onReceivedMock: (List<Item>) -> Unit = mock()
        remote.getAllItems(type, onReceivedMock)
        argumentCaptor<List<Item>>().apply {
            verify(onReceivedMock, times(1)).invoke(capture())
            assertEquals(tvShows, firstValue)
        }
    }

    @Test
    fun getMovieContent() {
        val id = movieId ?: 0
        val type = Type.MOVIE

        whenever(remote.getAllItems(eq(type), any())).thenAnswer {
            val callback = it.getArgument<((tvShows: List<Item>) -> Unit)>(1)
            callback.invoke(movies)
        }
        val result = itemRepository.getContent(id, type).getData()
        assertEquals(movies[0], result)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getTvShowContent() {
        val id = tvShowId ?: 0
        val type = Type.TV_SHOW

        whenever(remote.getAllItems(eq(type), any())).thenAnswer {
            val callback = it.arguments[1]
            val completion = callback as ((tvShows: List<Item>?) -> Unit)
            completion.invoke(tvShows)
        }
        val result = itemRepository.getContent(id, type).getData()
        assertEquals(tvShows[0], result)
    }
}
