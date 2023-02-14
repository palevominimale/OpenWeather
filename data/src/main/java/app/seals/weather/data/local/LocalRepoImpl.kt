package app.seals.weather.data.local

import android.content.Context
import app.seals.weather.data.mapToData
import app.seals.weather.data.mapToDomain
import app.seals.weather.data.models.WeatherResponseDataModel
import app.seals.weather.domain.LocalRepo
import app.seals.weather.domain.models.AutocompleteDomainModel

class LocalRepoImpl(context: Context) : LocalRepo {
    private var db = WeatherDB.getInstance(context)?.dao()!!

    override fun clearForecasts() = db.clear()
    fun saveForecast(forecast: WeatherResponseDataModel) = db.save(forecast)
    override fun loadForecast(id: String) = db.load(id)?.mapToDomain()
    override fun loadLocations() : List<AutocompleteDomainModel> = db.getLocations().mapToDomain()
    override fun addLocation(location: AutocompleteDomainModel) = db.addLocation(location.mapToData())
    override fun deleteLocation(id:Int) = db.deleteLocation(id)
}