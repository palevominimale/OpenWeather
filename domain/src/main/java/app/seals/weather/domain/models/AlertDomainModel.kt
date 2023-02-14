package app.seals.weather.domain.models

data class AlertDomainModel(
    var headline    : String? = null,
    var msgtype     : String? = null,
    var severity    : String? = null,
    var urgency     : String? = null,
    var areas       : String? = null,
    var category    : String? = null,
    var certainty   : String? = null,
    var event       : String? = null,
    var note        : String? = null,
    var effective   : String? = null,
    var expires     : String? = null,
    var desc        : String? = null,
    var instruction : String? = null
)
