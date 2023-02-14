package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class ConditionDataModel(
    @SerializedName("text" ) val text : String? = null,
    @SerializedName("code" ) val code : Int?    = null
)
