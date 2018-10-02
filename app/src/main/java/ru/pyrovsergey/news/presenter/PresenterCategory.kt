package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.fragments.NetworkDataCategoryListeners
import ru.pyrovsergey.news.model.dto.ArticlesItem

@InjectViewState
class CategoryPresenter : MvpPresenter<CategoryView>(), NetworkDataCategoryListeners {

    private val networkData = App.component.getNetworkData()
    private val repository = App.component.getRepository()

    fun getGeneralList(): List<ArticlesItem> = repository.generalList
    fun getEntertainmentList(): List<ArticlesItem> = repository.entertainmentList
    fun getSportsList(): List<ArticlesItem> = repository.sportsList
    fun getTechnologyList(): List<ArticlesItem> = repository.technologyList
    fun getHealthList(): List<ArticlesItem> = repository.healthList
    fun getBusinessList(): List<ArticlesItem> = repository.businessList
    fun getTopHeadlineList(): List<ArticlesItem> = repository.listHeadlinesNews

    fun prepareContent(category: String, page: Int) {
        networkData.getCategoryArticles(category, this, page)
    }

    override fun onSuccess(list: List<ArticlesItem>, category: Int) {
        when (category) {
            1 -> repository.generalList = list
            2 -> repository.entertainmentList = list
            3 -> repository.sportsList = list
            4 -> repository.technologyList = list
            5 -> repository.healthList = list
            6 -> repository.businessList = list
        }
        viewState.updateListArticles(category)
    }

    override fun onError(error: String) {
        viewState.showMessage(error)
    }
}

interface CategoryView: MvpView {
    fun showMessage(message: String)
    fun updateListArticles(page: Int)
}