package app.seals.weather.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import app.seals.weather.domain.LocationProvider
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LocationProviderImpl(
    private val context: Context
) : LocationProvider {

    private val _location = MutableSharedFlow<Location>()
    override val location: SharedFlow<Location> get() = _location

    private val flc = LocationServices.getFusedLocationProviderClient(context)
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            flc.lastLocation.addOnCompleteListener { scope.launch { _location.emit(it.result) } }
        }
    }
}