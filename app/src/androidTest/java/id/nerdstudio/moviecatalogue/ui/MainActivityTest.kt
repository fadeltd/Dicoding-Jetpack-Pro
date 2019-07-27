package id.nerdstudio.moviecatalogue.ui

import androidx.annotation.UiThread
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.fragment_main.*
import id.nerdstudio.moviecatalogue.R
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.notNullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    private lateinit var activity: MainActivity
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)
    private lateinit var adapter: ViewPagerAdapter

    @Before
    fun setUp() {
        activity = activityRule.activity
        assertThat(activity, notNullValue())

        adapter = (activity.view_pager.adapter) as ViewPagerAdapter
    }

    @After
    fun tearDown() {
    }

    /**
     * Menguji apakah tab layout sudah muncul
     */
    @Test
    fun checkTabLayoutDisplayed() {
        onView(withId(R.id.tab_layout)).perform(click()).check(matches(isDisplayed()))
    }

    /**
     * Menguji tab pertama memiliki judul Movies
     */
    @Test
    fun checkMovieTabTitle() {
        checkTabTitle(0, R.string.movies)
    }

    /**
     * Menguji tab kedua memiliki judul TV Shows
     */
    @Test
    fun checkTvTabTitle() {
        checkTabTitle(1, R.string.tv_shows)
    }

    /**
     * Untuk menguji apakah judul tab layout sudah sesuai
     */
    @UiThread
    fun checkTabTitle(position: Int, resString: Int) {
        val tabTitle = activity.resources.getString(resString)
        onView(allOf(withText(tabTitle), isDescendantOfA(withId(R.id.tab_layout))))
            .perform(click())
            .check(matches(isDisplayed()))

        val title = adapter.getPageTitle(position)
        assertThat(title.toString(), Matchers.equalTo(tabTitle))
    }
}