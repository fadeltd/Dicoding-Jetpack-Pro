package id.nerdstudio.moviecatalogue.ui.detail

import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DetailViewModelTest {
    private lateinit var viewModelMovie: DetailViewModel
    private lateinit var typeMovie: Type
    private lateinit var mockMovie: Item

    private lateinit var viewModelTv: DetailViewModel
    private lateinit var typeTv: Type
    private lateinit var mockTv: Item

    @Before
    fun setUp() {
        viewModelMovie = DetailViewModel()
        typeMovie = Type.MOVIE
        mockMovie = Item(
            id = 332562,
            voteAverage = 7.5F,
            title = "A Star Is Born",
            posterPath = "movie_a_star_is_born",
            overview = "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            releaseDate = "October 3, 2018"
        )

        viewModelTv = DetailViewModel()
        typeTv = Type.TV_SHOW
        mockTv = Item(
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
        viewModelMovie.id = mockMovie.id
        viewModelMovie.type = typeMovie
        val movie = viewModelMovie.getItem()
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
        viewModelTv.id = mockTv.id
        viewModelTv.type = typeTv
        val tvShow = viewModelTv.getItem()
        assertNotNull(tvShow)
        assertEquals(mockTv.id, tvShow?.id)
        assertEquals(mockTv.voteAverage, tvShow?.voteAverage)
        assertEquals(mockTv.title, tvShow?.title)
        assertEquals(mockTv.posterPath, tvShow?.posterPath)
        assertEquals(mockTv.overview, tvShow?.overview)
        assertEquals(mockTv.releaseDate, tvShow?.releaseDate)
    }
}