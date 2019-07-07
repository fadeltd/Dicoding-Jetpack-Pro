package id.nerdstudio.moviecatalogue.ui.content

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.testing.SingleFragmentActivity
import id.nerdstudio.moviecatalogue.util.EspressoIdlingResource
import id.nerdstudio.moviecatalogue.util.RecyclerViewItemCountAssertion
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListFragmentTest {
    private lateinit var activity: SingleFragmentActivity
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(SingleFragmentActivity::class.java)
    private val movieFragment = ListFragment.newInstance(Type.MOVIE)
    private val tvShowFragment = ListFragment.newInstance(Type.TV_SHOW)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoIdlingResource)
        activity = activityRule.activity
        assertThat(activity, Matchers.notNullValue())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoIdlingResource)
    }

    @Test
    fun loadMovies() {
        activityRule.activity.setFragment(movieFragment)
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).check(RecyclerViewItemCountAssertion(19))
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    @Test
    fun loadTvShows() {
        activityRule.activity.setFragment(tvShowFragment)
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view)).check(RecyclerViewItemCountAssertion(20))
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }
}