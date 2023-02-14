package app.seals.weather.data.prefs

import android.content.Context
import app.seals.weather.domain.models.PrefsProvider

class PrefsProviderImpl(
    context: Context
) : PrefsProvider {

    private val selectedLocation = "selected"
    private val prefs = context.getSharedPreferences("locations", Context.MODE_PRIVATE)

    override suspend fun getSelectedLocation(): String {
        return prefs.getString(selectedLocation, "0.0,0.0") ?: "0.0,0.0"
    }

    override suspend fun setSelectedLocation(id: String) {
        prefs.edit().putString(selectedLocation, id).apply()
    }
}