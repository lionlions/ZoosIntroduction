package com.cindy.zoosintroduction.model

data class ZoosModel(
    var data: ZoosIntroduction? = null
)

data class ZoosIntroduction(
    var zoos_list: List<Zoo>? = null
)

data class Zoo(
    var E_no: Int = -1,
    var E_Category: String? = null,
    var E_Name: String? = null,
    var E_Pic_URL: String? = null,
    var E_Info: String? = null,
    var E_Memo: String? = null,
    var E_Geo: String? = null,
    var E_URL: String? = null
)