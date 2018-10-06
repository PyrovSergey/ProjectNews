package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@InjectViewState
class HeadPresenter : MvpPresenter<HeadView>() {

    fun clickNewsList() {
        viewState.openNewsList()
    }

    fun clickCategoryList() {
        viewState.openCategoryList()
    }

    fun clickBookmarksList() {
        viewState.openBookmarksList()
    }
}


interface HeadView : MvpView {

    fun openNewsList()
    fun openCategoryList()
    fun openBookmarksList()
}