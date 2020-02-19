package com.aveyon.meivsm.model

data class CrowdfundingTemplateParameters(
    var receiver: String = "",
    var title: String = "",
    var goal: String = "",
    var unit: String = "",
    var endDate: Long = 0
)