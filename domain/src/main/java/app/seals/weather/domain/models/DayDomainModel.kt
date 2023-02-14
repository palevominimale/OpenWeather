package app.seals.weather.domain.models

data class DayDomainModel(
    var maxtempC          : Double?                 = null,
    var mintempC          : Double?                 = null,
    var avgtempC          : Double?                 = null,
    var maxwindKph        : Double?                 = null,
    var totalprecipMm     : Double?                 = null,
    var totalsnowCm       : Double?                 = null,
    var avgvisKm          : Double?                 = null,
    var avghumidity       : Double?                 = null,
    var dailyWillItRain   : Int?                    = null,
    var dailyChanceOfRain : Double?                 = null,
    var dailyWillItSnow   : Int?                    = null,
    var dailyChanceOfSnow : Double?                 = null,
    var condition         : ConditionDomainModel?   = null,
    var uv                : Double?                 = null
)