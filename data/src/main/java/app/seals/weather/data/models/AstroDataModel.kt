package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class AstroDataModel(
    @SerializedName("sunrise" ) val sunrise : String? = null,
    @SerializedName("sunset"  ) val sunset  : String? = null
)
