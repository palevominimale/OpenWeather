package app.seals.weather.domain.models

data class CurrentWeatherDomainModel(
    val lastUpdatedEpoch             : Int?                  = null,
    val lastUpdated                  : String?               = null,
    val tempC                        : Double?               = null,
    val isDay                        : Int?                  = null,
    val condition                    : ConditionDomainModel? = null,
    val windKph                      : Double?               = null,
    val windDegree                   : Int?                  = null,
    val windDir                      : String?               = null,
    val pressureMb                   : Double?               = null,
    val precipMm                     : Double?               = null,
    val humidity                     : Double?               = null,
    val cloud                        : Int?                  = null,
    val feelslikeC                   : Double?               = null,
    val visKm                        : Double?               = null,
    val uv                           : Double?               = null,
    val gustKph                      : Double?               = null,
    val airQuality                   : AqiDomainModel?       = null,
)
