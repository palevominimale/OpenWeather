package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class LanguageItemDataModel(
    @SerializedName("lang_name"  ) var langName  : String? = null,
    @SerializedName("lang_iso"   ) var langIso   : String? = null,
    @SerializedName("day_text"   ) var dayText   : String? = null,
    @SerializedName("night_text" ) var nightText : String? = null
)