package app.seals.weather.domain.models

data class AqiDomainModel(
    var co             : Double? = null,
    var no2            : Double? = null,
    var o3             : Double? = null,
    var so2            : Double? = null,
    var pm25           : Double? = null,
    var pm10           : Double? = null,
    var usEpaIndex     : Int?    = null,
    var gbDefraIndex   : Int?    = null
)
