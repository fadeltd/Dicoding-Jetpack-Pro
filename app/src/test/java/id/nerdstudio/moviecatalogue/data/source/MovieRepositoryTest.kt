package id.nerdstudio.moviecatalogue.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow
import id.nerdstudio.moviecatalogue.data.source.favorite.FavoriteRepository
import id.nerdstudio.moviecatalogue.data.source.local.LocalRepository
import id.nerdstudio.moviecatalogue.data.source.remote.RemoteRepository
import id.nerdstudio.moviecatalogue.util.AppExecutors
import id.nerdstudio.moviecatalogue.util.Dummy
import id.nerdstudio.moviecatalogue.util.getData
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieRepositoryTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val local: LocalRepository = mock()
    private val remote: RemoteRepository = mock()
    private val favorite: FavoriteRepository = mock()
    private val appExecutor: AppExecutors = mock()
    private val itemRepository = CatalogueRepository(local, remote, favorite, appExecutor)

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
        whenever(local.getAllMovies(any())).thenAnswer {
            val callback = it.getArgument<((movies: List<Movie>) -> Unit)>(0)
            callback.invoke(movies)
        }
        val result = itemRepository.getAllMovies().value
        assertEquals(movies.size, result?.size)

        val onReceivedMock: (List<Movie>) -> Unit = mock()
        local.getAllMovies(onReceivedMock)
        argumentCaptor<List<Movie>>().apply {
            verify(onReceivedMock, times(1)).invoke(capture())
            assertEquals(movies, firstValue)
        }
    }

    @Test
    fun getAllTvShows() {
        whenever(local.getAllTvShow(any())).thenAnswer {
            val callback = it.getArgument<((tvShows: List<TvShow>) -> Unit)>(0)
            callback.invoke(tvShows)
        }
        val result = itemRepository.getAllTvShows().value
        assertEquals(tvShows.size, result?.size)

        val onReceivedMock: (List<TvShow>) -> Unit = mock()
        local.getAllTvShow(onReceivedMock)
        argumentCaptor<List<TvShow>>().apply {
            verify(onReceivedMock, times(1)).invoke(capture())
            assertEquals(tvShows, firstValue)
        }
    }

    @Test
    fun getMovieContent() {
        val id = movieId
        whenever(local.getAllMovies(any())).thenAnswer {
            val callback = it.getArgument<((tvShows: List<Movie>) -> Unit)>(0)
            callback.invoke(movies)
        }
        val result = itemRepository.getMovieContent(id).getData()
        assertEquals(movies[0], result)
    }

    @Test
    fun getTvShowContent() {
        val id = tvShowId
        whenever(local.getAllTvShow(any())).thenAnswer {
            val callback = it.getArgument<((tvShows: List<TvShow>?) -> Unit)>(0)
            callback.invoke(tvShows)
        }
        val result = itemRepository.getTvShowContent(id).getData()
        assertEquals(tvShows[0], result)
    }
}
