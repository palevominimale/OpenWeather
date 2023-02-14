package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class AqiDataModel(
    @SerializedName("co"             ) val co             : Double? = null,
    @SerializedName("no2"            ) val no2            : Double? = null,
    @SerializedName("o3"             ) val o3             : Double? = null,
    @SerializedName("so2"            ) val so2            : Double? = null,
    @SerializedName("pm2_5"          ) val pm25           : Double? = null,
    @SerializedName("pm10"           ) val pm10           : Double? = null,
    @SerializedName("us-epa-index"   ) val usEpaIndex     : Int?    = null,
    @SerializedName("gb-defra-index" ) val gbDefraIndex   : Int?    = null
)
