package app.seals.weather.data.api

import app.seals.weather.data.models.AutocompleteDataModel
import app.seals.weather.data.models.WeatherResponseDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteApi {

    @GET("forecast.json?aqi=$DEF_AQI&alerts=$DEF_ALERTS")
    fun getForecast(
        @Query("key") key: String,
        @Query("days") days: Int = 10,          //1-10
        @Query("q") location: String,
    ) : Call<WeatherResponseDataModel>

    @GET("search.json?aqi=$DEF_AQI&alerts=$DEF_ALERTS")
    fun getAutocomplete(
        @Query("key") key: String,
        @Query("q") query: String,
    ) : Call<ArrayList<AutocompleteDataModel>>

    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
        private const val DEF_AQI = "yes"
        private const val DEF_ALERTS = "yes"
    }
}