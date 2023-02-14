package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class LanguagesResponseDataModel(
    @SerializedName("code"      ) var code      : Int?                 = null,
    @SerializedName("day"       ) var day       : String?              = null,
    @SerializedName("night"     ) var night     : String?              = null,
    @SerializedName("icon"      ) var icon      : Int?                 = null,
    @SerializedName("languages" ) var languages : ArrayList<LanguageItemDataModel> = arrayListOf()

)
