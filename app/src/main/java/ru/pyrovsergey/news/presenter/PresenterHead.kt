package ru.pyrovsergey.news.presenter

import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.pyrovsergey.news.di.App

@InjectViewState
class HeadPresenter : MvpPresenter<HeadView>() {
    private val router = App.getInstance().getRouter()
    private var firstCall = true

    fun prepareScreen() {
        if (firstCall) {
            firstCall = false
            router.replaceScreen("1")
        }
    }

    fun closeSearchFragment() {
        router.exit()
        viewState.changeVisibleNavBar(View.VISIBLE)
    }

    fun clickNewsList(barTitle: String) {
        router.replaceScreen("FragmentNews")
        viewState.changeToolBarTitle(barTitle)
    }

    fun clickCategoryList(barTitle: String) {
        router.replaceScreen("FragmentCategory")
        viewState.changeToolBarTitle(barTitle)
    }

    fun clickBookmarksList(barTitle: String) {
        router.replaceScreen("FragmentBookmarks")
        viewState.changeToolBarTitle(barTitle)
    }

    fun clickSearchList(query: String) {
        router.navigateTo("FragmentNewsSearch", query)
        viewState.changeVisibleNavBar(View.INVISIBLE)
    }
}

interface HeadView : MvpView {
    fun changeToolBarTitle(title: String)
    fun changeVisibleNavBar(visible: Int)
}