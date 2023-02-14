package app.seals.weather.data.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class HourDataModel(
    @PrimaryKey @SerializedName("time_epoch"     ) var timeEpoch    : Int?                  = null,
    @SerializedName("time"           ) val time                     : String?               = null,
    @SerializedName("temp_c"         ) val tempC                    : Double?               = null,
    @SerializedName("is_day"         ) val isDay                    : Int?                  = null,
    @SerializedName("condition"      ) val condition                : ConditionDataModel?   = ConditionDataModel(),
    @SerializedName("wind_kph"       ) val windKph                  : Double?               = null,
    @SerializedName("wind_degree"    ) val windDegree               : Double?               = null,
    @SerializedName("wind_dir"       ) val windDir                  : String?               = null,
    @SerializedName("pressure_mb"    ) val pressureMb               : Double?               = null,
    @SerializedName("precip_mm"      ) val precipMm                 : Double?               = null,
    @SerializedName("humidity"       ) val humidity                 : Double?               = null,
    @SerializedName("cloud"          ) val cloud                    : Int?                  = null,
    @SerializedName("feelslike_c"    ) val feelslikeC               : Double?               = null,
    @SerializedName("windchill_c"    ) val windchillC               : Double?               = null,
    @SerializedName("heatindex_c"    ) val heatindexC               : Double?               = null,
    @SerializedName("dewpoint_c"     ) val dewpointC                : Double?               = null,
    @SerializedName("will_it_rain"   ) val willItRain               : Int?                  = null,
    @SerializedName("chance_of_rain" ) val chanceOfRain             : Double?               = null,
    @SerializedName("will_it_snow"   ) val willItSnow               : Int?                  = null,
    @SerializedName("chance_of_snow" ) val chanceOfSnow             : Double?               = null,
    @SerializedName("vis_km"         ) val visKm                    : Double?               = null,
    @SerializedName("gust_kph"       ) val gustKph                  : Double?               = null,
    @SerializedName("uv"             ) val uv                       : Double?               = null

)
