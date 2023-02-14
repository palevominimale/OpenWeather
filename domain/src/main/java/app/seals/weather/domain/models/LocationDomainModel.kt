package app.seals.weather.domain.models

data class LocationDomainModel(
    val name           : String? = null,
    val region         : String? = null,
    val country        : String? = null,
    val lat            : Double? = null,
    val lon            : Double? = null,
    val tzId           : String? = null,
    val localtimeEpoch : Int?    = null,
    val localtime      : String? = null
)
