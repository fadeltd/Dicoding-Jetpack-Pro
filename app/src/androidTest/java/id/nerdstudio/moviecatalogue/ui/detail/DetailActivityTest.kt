package id.nerdstudio.moviecatalogue.ui.detail

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.ui.main.PageType
import id.nerdstudio.moviecatalogue.util.Dummy
import id.nerdstudio.moviecatalogue.util.EspressoIdlingResource
import id.nerdstudio.moviecatalogue.util.parseDate
import id.nerdstudio.moviecatalogue.util.toFormattedDate
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailActivityTest {
    private val mockMovie = Dummy.dummyMovies()[1]

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<DetailActivity> =
        object : ActivityTestRule<DetailActivity>(DetailActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                return Intent(targetContext, DetailActivity::class.java)
                    .putExtra(DetailActivity.ARG_ID, mockMovie.id)
                    .putExtra(DetailActivity.ARG_TYPE, Type.MOVIE)
                    .putExtra(DetailActivity.ARG_PAGE_TYPE, PageType.TEST)
            }
        }

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun loadMovieDetail() {
        val mockTitle = "${mockMovie.title} (${mockMovie.releaseDate?.parseDate()?.year})"
        val mockReleaseDate = mockMovie.releaseDate.toFormattedDate()
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withText(mockTitle)).check(matches(withParent(withId(R.id.toolbar))))
        onView(withId(R.id.movie_title)).check(matches(withText(mockMovie.title)))
        onView(withId(R.id.movie_release_date)).check(matches(withText(mockReleaseDate)))
        onView(withId(R.id.movie_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_description)).check(matches(withText(mockMovie.overview)))
    }
}