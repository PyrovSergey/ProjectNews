package ru.pyrovsergey.news.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import es.dmoral.toasty.Toasty
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.navigation_content_main.*
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.presenter.HeadPresenter
import ru.pyrovsergey.news.presenter.HeadView
import ru.pyrovsergey.news.ui.fragments.FragmentBookmarks
import ru.pyrovsergey.news.ui.fragments.FragmentCategory
import ru.pyrovsergey.news.ui.fragments.FragmentNews
import ru.pyrovsergey.news.ui.fragments.FragmentNewsSearch


class MainActivity : MvpAppCompatActivity(), HeadView {

    @InjectPresenter
    lateinit var presenter: HeadPresenter
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        initialization()
    }

    private fun initialization() {
        Fabric.with(this, Crashlytics())
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        supportActionBar?.title = ""
        setSupportActionBar(toolbar)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.navigationBarColor = getColor(R.color.colorWhite)
        } else {
            window.navigationBarColor = getColor(R.color.colorLightGrey)
        }
        val fragment = FragmentNews.newInstance()
        replaceFragment(fragment)
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        App.instance.checkInternetConnection()
        Toasty.Config.getInstance()
                .setSuccessColor(getColor(R.color.colorBlack))
                .setTextColor(getColor(R.color.colorWhite))
                .apply()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchCloseIcon = searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
        searchCloseIcon.setImageResource(R.drawable.ic_close_black_24dp)
        val searchEditText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(getColor(R.color.colorBlack))
        searchEditText.setHintTextColor(getColor(R.color.colorBlackOverlay))
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (App.instance.checkInternetConnection()) {
                    addSearchFragment(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchView.setOnQueryTextFocusChangeListener { focus, hasFocus ->
            if (hasFocus) {
                navigation.visibility = View.GONE
            } else {
                removeSearchFragment()
                searchView.onActionViewCollapsed()
                navigation.visibility = View.VISIBLE
            }
        }
        return true
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_news -> {
                presenter.clickNewsList()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_category -> {
                presenter.clickCategoryList()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bookmarks -> {
                presenter.clickBookmarksList()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun openNewsList() {
        val fragment = FragmentNews.newInstance()
        replaceFragment(fragment)
        toolbarTitle?.setText(R.string.title_news)
    }

    override fun openCategoryList() {
        val fragment = FragmentCategory()
        replaceFragment(fragment)
        toolbarTitle?.setText(R.string.title_categories)
    }

    override fun openBookmarksList() {
        val fragment = FragmentBookmarks()
        replaceFragment(fragment)
        toolbarTitle?.setText(R.string.title_bookmarks)
    }

    @SuppressLint("PrivateResource")
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(navigationContentFrame.id, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    @SuppressLint("PrivateResource")
    private fun addSearchFragment(query: String) {
        val fragment = FragmentNewsSearch.newInstance(query)
        navigation.visibility = View.GONE
        navigationContentFrame.visibility = View.INVISIBLE
        searchContentFrame.visibility = View.VISIBLE
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(searchContentFrame.id, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    private fun removeSearchFragment() {
        navigation.visibility = View.VISIBLE
        navigationContentFrame.visibility = View.VISIBLE
        searchContentFrame.visibility = View.INVISIBLE
    }
}
