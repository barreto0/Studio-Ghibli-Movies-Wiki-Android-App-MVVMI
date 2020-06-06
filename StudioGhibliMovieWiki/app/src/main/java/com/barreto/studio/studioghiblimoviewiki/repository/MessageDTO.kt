package com.barreto.studio.studioghiblimoviewiki.repository


import com.google.gson.annotations.SerializedName

data class MessageDTO(
    @SerializedName("queryResult")
    val queryResult : QueryResult
)

data class QueryResult (
    @SerializedName("fulfillmentText")
    val fulfillmentText : String
)