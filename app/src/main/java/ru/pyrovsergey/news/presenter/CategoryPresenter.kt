package ru.pyrovsergey.news.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.pyrovsergey.news.di.App
import ru.pyrovsergey.news.fragments.NetworkDataCategoryListeners
import ru.pyrovsergey.news.model.dto.Model

@InjectViewState
class CategoryPresenter : MvpPresenter<CategoryView>(), NetworkDataCategoryListeners {

    private val networkData = App.component.getNetworkData()
    private val dataStorage = App.component.getDataStorage()

    fun getGeneralList(): List<Model.ArticlesItem> = dataStorage.generalList
    fun getEntertainmentList(): List<Model.ArticlesItem> = dataStorage.entertainmentList
    fun getSportsList(): List<Model.ArticlesItem> = dataStorage.sportsList
    fun getTechnologyList(): List<Model.ArticlesItem> = dataStorage.technologyList
    fun getHealthList(): List<Model.ArticlesItem> = dataStorage.healthList
    fun getBusinessList(): List<Model.ArticlesItem> = dataStorage.businessList
    fun getTopHeadlineList(): List<Model.ArticlesItem> = dataStorage.listHeadlinesNews

    fun prepareContent(category: String, page: Int) {
        networkData.getCategoryArticles(category, this, page)
    }

    override fun onSuccess(list: List<Model.ArticlesItem>, category: Int) {
        when (category) {
            1 -> dataStorage.generalList = list
            2 -> dataStorage.entertainmentList = list
            3 -> dataStorage.sportsList = list
            4 -> dataStorage.technologyList = list
            5 -> dataStorage.healthList = list
            6 -> dataStorage.businessList = list
        }
        viewState.updateListArticles(category)
    }

    override fun onError(error: String) {
        viewState.showMessage(error)
    }
}