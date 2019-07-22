package id.nerdstudio.moviecatalogue.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.data.entity.Type
import id.nerdstudio.moviecatalogue.ui.content.ListFragment
import id.nerdstudio.moviecatalogue.ui.main.PageType

class ViewPagerAdapter(
    private val context: Context,
    private val pageType: PageType,
    fragmentManager: FragmentManager
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> ListFragment.newInstance(Type.MOVIE, pageType)
            1 -> ListFragment.newInstance(Type.TV_SHOW, pageType)
            else -> null
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.getString(
            when (position) {
                0 -> R.string.movies
                1 -> R.string.tv_shows
                else -> R.string.empty
            }
        )
    }
}
