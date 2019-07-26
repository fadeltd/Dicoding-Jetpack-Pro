package id.nerdstudio.moviecatalogue.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import id.nerdstudio.moviecatalogue.R
import id.nerdstudio.moviecatalogue.ui.MainActivity
import id.nerdstudio.moviecatalogue.ui.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {
    var pageType: PageType = PageType.HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            pageType = it.get(ARG_TYPE) as PageType
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).run {
            setSupportActionBar(toolbar)
            toolbar.title = pageType.type

            val viewPagerAdapter = ViewPagerAdapter(this, pageType, childFragmentManager)
            view_pager.adapter = viewPagerAdapter
            tab_layout.setupWithViewPager(view_pager)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).run {
            setSupportActionBar(toolbar)
            toolbar.title = pageType.type
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        menuInflater?.inflate(R.menu.menu_main, menu)
    }

    companion object {
        const val ARG_TYPE = "type"

        @JvmStatic
        fun newInstance(type: PageType) = MainFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_TYPE, type)
            }
        }
    }
}