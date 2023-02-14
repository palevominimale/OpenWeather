package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class ForecastDataModel(
    @SerializedName("forecastday" ) val forecastday : ArrayList<ForecastDayDataModel> = arrayListOf()
)