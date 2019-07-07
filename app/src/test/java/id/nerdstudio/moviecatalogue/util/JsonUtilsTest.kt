package id.nerdstudio.moviecatalogue.util

import com.google.gson.JsonParser
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.util.Dummy.dummyMovies
import id.nerdstudio.moviecatalogue.util.Dummy.dummyTvShows
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class JsonUtilsTest {
    private lateinit var parser: JsonParser

    @Before
    fun setup() {
        parser = JsonParser()
    }

    @Test
    fun safe() {
        val title = parser.parse("{\"title\": \"Shazam\"}").asJsonObject
        val releaseDate = parser.parse("{\"release_date\": \"2018-12-26\"}").asJsonObject
        val voteAverage = parser.parse("{\"vote_average\": 3.2}").asJsonObject

        assertEquals(title.safe("title"), true)
        assertEquals(title.safe("new"), false)
        assertEquals(releaseDate.safe("release_date"), true)
        assertEquals(releaseDate.safe("any"), false)
        assertEquals(voteAverage.safe("releaseDate"), false)
        assertEquals(voteAverage.safe("vote_average"), true)
        assertEquals(voteAverage.safe("voteAverage"), false)
    }

    @Test
    fun safeLong() {
        val id = parser.parse("{\"id\": 5234}").asJsonObject
        assertEquals(id.safeLong("id"), 5234L)
        assertEquals(id.safeLong("voteAverage"), 0L)
    }

    @Test
    fun safeFloat() {
        val voteAverage = parser.parse("{\"vote_average\": 3.2}").asJsonObject
        assertEquals(voteAverage.safeFloat("vote_average"), 3.2F)
        assertEquals(voteAverage.safeFloat("voteAverage"), 0F)
    }

    @Test
    fun loadItems() {
        val jsonUtils: JsonUtils = mock()
        val dummyMovies = dummyMovies()
        whenever(jsonUtils.loadItems(eq(Type.MOVIE))).thenReturn(dummyMovies)
        val movies = jsonUtils.loadItems(Type.MOVIE)
        assertEquals(movies, dummyMovies)

        val dummyTvShows = dummyTvShows()
        whenever(jsonUtils.loadItems(eq(Type.TV_SHOW))).thenReturn(dummyTvShows)
        val tvShows = jsonUtils.loadItems(Type.TV_SHOW)
        assertEquals(dummyTvShows, tvShows)
    }
}