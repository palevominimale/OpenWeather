package app.seals.weather.domain.models

data class ForecastDayDomainModel(
    val date      : String?                  = null,
    val dateEpoch : Int?                     = null,
    val day       : DayDomainModel?          = null,
    val astro     : AstroDomainModel?        = null,
    val hour      : ArrayList<HourDomainModel> = arrayListOf()
)