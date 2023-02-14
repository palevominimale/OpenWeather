package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class DayDataModel(
    @SerializedName("maxtemp_c"            ) val maxtempC          : Double?    = null,
    @SerializedName("mintemp_c"            ) val mintempC          : Double?    = null,
    @SerializedName("avgtemp_c"            ) val avgtempC          : Double?    = null,
    @SerializedName("maxwind_kph"          ) val maxwindKph        : Double?    = null,
    @SerializedName("totalprecip_mm"       ) val totalprecipMm     : Double?       = null,
    @SerializedName("totalsnow_cm"         ) val totalsnowCm       : Double?       = null,
    @SerializedName("avgvis_km"            ) val avgvisKm          : Double?    = null,
    @SerializedName("avghumidity"          ) val avghumidity       : Double?       = null,
    @SerializedName("daily_will_it_rain"   ) val dailyWillItRain   : Int?       = null,
    @SerializedName("daily_chance_of_rain" ) val dailyChanceOfRain : Double?       = null,
    @SerializedName("daily_will_it_snow"   ) val dailyWillItSnow   : Int?       = null,
    @SerializedName("daily_chance_of_snow" ) val dailyChanceOfSnow : Double?       = null,
    @SerializedName("condition"            ) val condition         : ConditionDataModel? = ConditionDataModel(),
    @SerializedName("uv"                   ) val uv                : Double?       = null
)