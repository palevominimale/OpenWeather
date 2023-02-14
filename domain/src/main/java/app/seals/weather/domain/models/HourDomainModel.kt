package app.seals.weather.domain.models

data class HourDomainModel(
    val timeEpoch                 : Int?                  = null,
    val  time                     : String?               = null,
    val  tempC                    : Double?               = null,
    val  isDay                    : Int?                  = null,
    val  condition                : ConditionDomainModel? = null,
    val  windKph                  : Double?               = null,
    val  windDegree               : Double?               = null,
    val  windDir                  : String?               = null,
    val  pressureMb               : Double?               = null,
    val  precipMm                 : Double?               = null,
    val  humidity                 : Double?               = null,
    val  cloud                    : Int?                  = null,
    val  feelslikeC               : Double?               = null,
    val  windchillC               : Double?               = null,
    val  heatindexC               : Double?               = null,
    val  dewpointC                : Double?               = null,
    val  willItRain               : Int?                  = null,
    val  chanceOfRain             : Double?               = null,
    val  willItSnow               : Int?                  = null,
    val  chanceOfSnow             : Double?               = null,
    val  visKm                    : Double?               = null,
    val  gustKph                  : Double?               = null,
    val  uv                       : Double?               = null
)
