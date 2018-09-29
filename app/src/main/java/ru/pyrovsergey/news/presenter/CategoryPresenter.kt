package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.fragments.NetworkDataCategoryListeners
import ru.pyrovsergey.news.model.dto.Model

@InjectViewState
class CategoryPresenter : MvpPresenter<CategoryView>(), NetworkDataCategoryListeners {

    private val networkData = App.component.getNetworkData()
    private val repository = App.component.getRepository()

    fun getGeneralList(): List<Model.ArticlesItem> = repository.generalList
    fun getEntertainmentList(): List<Model.ArticlesItem> = repository.entertainmentList
    fun getSportsList(): List<Model.ArticlesItem> = repository.sportsList
    fun getTechnologyList(): List<Model.ArticlesItem> = repository.technologyList
    fun getHealthList(): List<Model.ArticlesItem> = repository.healthList
    fun getBusinessList(): List<Model.ArticlesItem> = repository.businessList
    fun getTopHeadlineList(): List<Model.ArticlesItem> = repository.listHeadlinesNews

    fun prepareContent(category: String, page: Int) {
        networkData.getCategoryArticles(category, this, page)
    }

    override fun onSuccess(list: List<Model.ArticlesItem>, category: Int) {
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