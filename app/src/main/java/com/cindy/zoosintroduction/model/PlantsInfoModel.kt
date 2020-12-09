package com.cindy.zoosintroduction.model

data class PlantsInfoModel(
    var result: Result = Result()
)

data class Result(
    var count: Int = 0,
    var limit: Int = 0,
    var offset: Int = 0,
    var results: List<ResultX> = listOf(),
    var sort: String = ""
)

data class ResultX(
    var F_AlsoKnown: String = "",
    var F_Brief: String = "",
    var F_CID: String = "",
    var F_Code: String = "",
    var F_Family: String = "",
    var F_Feature: String = "",
//    var F_Function&Application: String = "",
    var F_Genus: String = "",
    var F_Geo: String = "",
    var F_Keywords: String = "",
    var F_Location: String = "",
    var F_Name_En: String = "",
    var F_Name_Latin: String = "",
    var F_Pic01_ALT: String = "",
    var F_Pic01_URL: String = "",
    var F_Pic02_ALT: String = "",
    var F_Pic02_URL: String = "",
    var F_Pic03_ALT: String = "",
    var F_Pic03_URL: String = "",
    var F_Pic04_ALT: String = "",
    var F_Pic04_URL: String = "",
    var F_Summary: String = "",
    var F_Update: String = "",
    var F_Vedio_URL: String = "",
    var F_Voice01_ALT: String = "",
    var F_Voice01_URL: String = "",
    var F_Voice02_ALT: String = "",
    var F_Voice02_URL: String = "",
    var F_Voice03_ALT: String = "",
    var F_Voice03_URL: String = "",
    var F_pdf01_ALT: String = "",
    var F_pdf01_URL: String = "",
    var F_pdf02_ALT: String = "",
    var F_pdf02_URL: String = "",
    var _full_count: String = "",
    var _id: Int = 0,
    var F_Name_Ch: String = "",
    var rank: Double = 0.0
)