package id.nerdstudio.moviecatalogue.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.ui.main.MainFragment
import id.nerdstudio.moviecatalogue.ui.main.PageType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private var firstOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
        bottom_navigation_view.selectedItemId =
            savedInstanceState?.getInt(SELECTED_MENU) ?: R.id.nav_home
        firstOpen = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_MENU, bottom_navigation_view.selectedItemId)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(R.id.fragment_container, fragment, fragment::class.java.simpleName)
            .commit()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val fragment = MainFragment.newInstance(
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    PageType.HOME
                }
                else -> PageType.FAVORITE
            }
        )
        val changed = bottom_navigation_view.selectedItemId != menuItem.itemId
        if (firstOpen || changed) {
            replaceFragment(fragment)
        }
        return true
    }

    companion object {
        private const val SELECTED_MENU = "selected_menu"
    }
}