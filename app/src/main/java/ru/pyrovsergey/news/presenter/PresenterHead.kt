package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView

@InjectViewState
class HeadPresenter : MvpPresenter<HeadView>() {

}

interface HeadView : MvpView {
}