package app.seals.weather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "forecast")
data class WeatherResponseDataModel(
    @PrimaryKey                  val id       : String                      = "0",
    @SerializedName("location" ) val location : LocationDataModel?          = LocationDataModel(),
    @SerializedName("current"  ) val current  : CurrentWeatherDataModel?    = CurrentWeatherDataModel(),
    @SerializedName("forecast" ) val forecast : ForecastDataModel?          = ForecastDataModel(),
    @SerializedName("alerts"   ) val alerts   : AlertsDataModel?            = AlertsDataModel()
)
