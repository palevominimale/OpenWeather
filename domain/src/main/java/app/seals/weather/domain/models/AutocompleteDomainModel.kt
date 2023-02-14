package app.seals.weather.domain.models

data class AutocompleteDomainModel(
    var id      : Int     = 0,
    var name    : String? = null,
    var region  : String? = null,
    var country : String? = null,
    var lat     : Double? = null,
    var lon     : Double? = null,
    var url     : String? = null
)
