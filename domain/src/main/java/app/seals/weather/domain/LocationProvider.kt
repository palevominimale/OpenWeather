package app.seals.weather.domain

import android.location.Location
import kotlinx.coroutines.flow.SharedFlow

interface LocationProvider {
    val location: SharedFlow<Location>
    fun getLocation()
}