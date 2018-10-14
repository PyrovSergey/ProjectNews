package ru.pyrovsergey.news.ui

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
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
import ru.pyrovsergey.news.R
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.presenter.HeadPresenter
import ru.pyrovsergey.news.presenter.HeadView
import ru.pyrovsergey.news.ui.fragments.FragmentBookmarks
import ru.pyrovsergey.news.ui.fragments.FragmentCategory
import ru.pyrovsergey.news.ui.fragments.FragmentNews
import ru.pyrovsergey.news.ui.fragments.FragmentNewsSearch
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Command


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
            window.navigationBarColor = getColor(R.color.colorWhite)
        } else {
            window.navigationBarColor = getColor(R.color.colorLightGrey)
        }
        presenter.prepareScreen()
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        App.getInstance().checkInternetConnection()
        Toasty.Config.getInstance()
                .setSuccessColor(getColor(R.color.colorBlack))
                .setTextColor(getColor(R.color.colorWhite))
                .apply()
    }

    private val navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.navigationContentFrame) {
        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            return when (screenKey) {
                "FragmentNews" -> FragmentNews.newInstance()
                "FragmentCategory" -> FragmentCategory.newInstance()
                "FragmentBookmarks" -> FragmentBookmarks.newInstance()
                "FragmentNewsSearch" -> FragmentNewsSearch.newInstance(data as String)
                else -> FragmentNews.newInstance()
            }
        }

        override fun exit() {
        }

        override fun showSystemMessage(message: String?) {
            Toasty.error(this@MainActivity, message!!, 0, true).show()
        }

        override fun setupFragmentTransactionAnimation(command: Command?, currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction?) {
            fragmentTransaction?.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
        }
    }

    override fun onResume() {
        super.onResume()
        App.getInstance().getNavigationHolder().setNavigator(navigator)
    }

    override fun onPause() {
        App.getInstance().getNavigationHolder().removeNavigator()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        val searchCloseIcon = searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
        searchCloseIcon.visibility = View.INVISIBLE
        val searchEditText = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
        searchEditText.setTextColor(getColor(R.color.colorBlack))
        searchEditText.setHintTextColor(getColor(R.color.colorBlackOverlay))
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (App.getInstance().checkInternetConnection()) {
                    presenter.clickSearchList(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        searchView.setOnQueryTextFocusChangeListener { focus, hasFocus ->
            if (!hasFocus) {
                presenter.closeSearchFragment()
                searchView.onActionViewCollapsed()
            }
        }
        return true
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_news -> {
                presenter.clickNewsList(resources.getString(R.string.title_news))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_category -> {
                presenter.clickCategoryList(resources.getString(R.string.title_categories))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_bookmarks -> {
                presenter.clickBookmarksList(resources.getString(R.string.title_bookmarks))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun changeToolBarTitle(title: String) {
        toolbarTitle?.text = title
    }

    override fun changeVisibleNavBar(visible: Int) {
        navigation.visibility = visible
    }

    override fun onBackPressed() {
        changeVisibleNavBar(View.VISIBLE)
        super.onBackPressed()
    }
}
