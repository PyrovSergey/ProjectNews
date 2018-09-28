package ru.pyrovsergey.news

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_news.*
import ru.pyrovsergey.news.fragments.FragmentBookmarks
import ru.pyrovsergey.news.fragments.FragmentCategory
import ru.pyrovsergey.news.fragments.FragmentNews
import ru.pyrovsergey.news.presenter.NewsPresenter
import ru.pyrovsergey.news.presenter.NewsView

class NewsActivity : MvpAppCompatActivity(), NewsView {

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initialization()

//        var network = NetworkData()
    }


    private fun initialization() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val fragment = FragmentNews.newInstance()
        addFragment(fragment)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_news -> {
                val fragment = FragmentNews.newInstance()
                addFragment(fragment)
                toolbarTitle.setText(R.string.title_news)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_category -> {
                val fragment = FragmentCategory()
                addFragment(fragment)
                toolbarTitle.setText(R.string.title_categories)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bookmarks -> {
                val fragment = FragmentBookmarks()
                addFragment(fragment)
                toolbarTitle.setText(R.string.title_bookmarks)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.navigation_content, fragment, fragment.javaClass.simpleName)
                //.addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }
}
