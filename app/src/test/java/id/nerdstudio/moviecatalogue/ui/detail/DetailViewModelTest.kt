package id.nerdstudio.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import id.nerdstudio.moviecatalogue.data.entity.Item
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.data.source.CatalogueRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class DetailViewModelTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val itemRepository = mock(CatalogueRepository::class.java)

    private lateinit var viewModelMovie: DetailViewModel
    private lateinit var typeMovie: Type
    private lateinit var mockMovie: Item

    private lateinit var viewModelTvShow: DetailViewModel
    private lateinit var typeTvShow: Type
    private lateinit var mockTvShow: Item

    @Before
    fun setUp() {
        viewModelMovie = DetailViewModel(itemRepository)
        typeMovie = Type.MOVIE
        mockMovie = Item(
            id = 332562,
            voteAverage = 7.5F,
            title = "A Star Is Born",
            posterPath = "movie_a_star_is_born",
            overview = "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            releaseDate = "October 3, 2018"
        )

        viewModelTvShow = DetailViewModel(itemRepository)
        typeTvShow = Type.TV_SHOW
        mockTvShow = Item(
            id = 1402,
            title = "The Walking Dead",
            voteAverage = 7.26F,
            posterPath = "tv_the_walking_dead",
            releaseDate = "2010-10-31",
            overview = "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way."
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getItemMovie() {
        val movieData = MutableLiveData<Item>()
        movieData.value = mockMovie

        val id = mockMovie.id ?: 0L
        viewModelMovie.id = id
        viewModelMovie.type = typeMovie

        `when`(itemRepository.getContent(id, typeMovie)).thenReturn(movieData)

        val observer: Observer<Item> = mock()
        viewModelMovie.getItem()?.observeForever(observer)

        val movie = viewModelMovie.getItem()?.value
        assertNotNull(movie)
        assertEquals(mockMovie.id, movie?.id)
        assertEquals(mockMovie.voteAverage, movie?.voteAverage)
        assertEquals(mockMovie.title, movie?.title)
        assertEquals(mockMovie.posterPath, movie?.posterPath)
        assertEquals(mockMovie.overview, movie?.overview)
        assertEquals(mockMovie.releaseDate, movie?.releaseDate)
    }

    @Test
    fun getItemTvShow() {
        val tvShowData = MutableLiveData<Item>()
        tvShowData.value = mockTvShow

        val id = mockTvShow.id ?: 0L
        viewModelTvShow.id = id
        viewModelTvShow.type = typeTvShow

        `when`(itemRepository.getContent(id, typeTvShow)).thenReturn(tvShowData)

        val observer: Observer<Item> = mock()
        viewModelTvShow.getItem()?.observeForever(observer)

        val tvShow = viewModelTvShow.getItem()?.value
        assertNotNull(tvShow)
        assertEquals(mockTvShow.id, tvShow?.id)
        assertEquals(mockTvShow.voteAverage, tvShow?.voteAverage)
        assertEquals(mockTvShow.title, tvShow?.title)
        assertEquals(mockTvShow.posterPath, tvShow?.posterPath)
        assertEquals(mockTvShow.overview, tvShow?.overview)
        assertEquals(mockTvShow.releaseDate, tvShow?.releaseDate)
    }
}