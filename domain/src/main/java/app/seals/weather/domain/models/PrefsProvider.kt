package app.seals.weather.domain.models

interface PrefsProvider {
    suspend fun getSelectedLocation() : String
    suspend fun setSelectedLocation(id: String)
}