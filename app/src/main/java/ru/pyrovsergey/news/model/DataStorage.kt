package ru.pyrovsergey.news.model

import ru.pyrovsergey.news.model.dto.Model

class DataStorage {
    var listHeadlinesNews: List<Model.ArticlesItem> = mutableListOf()
    var generalList: List<Model.ArticlesItem> = mutableListOf()
    var entertainmentList: List<Model.ArticlesItem> = mutableListOf()
    var sportsList: List<Model.ArticlesItem> = mutableListOf()
    var technologyList: List<Model.ArticlesItem> = mutableListOf()
    var healthList: List<Model.ArticlesItem> = mutableListOf()
    var businessList: List<Model.ArticlesItem> = mutableListOf()
}