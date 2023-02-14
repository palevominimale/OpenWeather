package app.seals.weather.data.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CurrentWeatherDataModel(
    @PrimaryKey @SerializedName("last_updated_epoch" ) var lastUpdatedEpoch : Int?                  = null,
    @SerializedName("last_updated"       ) val lastUpdated                  : String?               = null,
    @SerializedName("temp_c"             ) val tempC                        : Double?               = null,
    @SerializedName("is_day"             ) val isDay                        : Int?                  = null,
    @SerializedName("condition"          ) val condition                    : ConditionDataModel?   = ConditionDataModel(),
    @SerializedName("wind_kph"           ) val windKph                      : Double?               = null,
    @SerializedName("wind_degree"        ) val windDegree                   : Int?                  = null,
    @SerializedName("wind_dir"           ) val windDir                      : String?               = null,
    @SerializedName("pressure_mb"        ) val pressureMb                   : Double?               = null,
    @SerializedName("precip_mm"          ) val precipMm                     : Double?               = null,
    @SerializedName("humidity"           ) val humidity                     : Double?               = null,
    @SerializedName("cloud"              ) val cloud                        : Int?                  = null,
    @SerializedName("feelslike_c"        ) val feelslikeC                   : Double?               = null,
    @SerializedName("vis_km"             ) val visKm                        : Double?               = null,
    @SerializedName("uv"                 ) val uv                           : Double?               = null,
    @SerializedName("gust_kph"           ) val gustKph                      : Double?               = null,
    @SerializedName("air_quality"        ) val airQuality                   : AqiDataModel?         = AqiDataModel()
)
