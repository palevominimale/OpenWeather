package app.seals.weather.domain.models

sealed interface ApiResult {
    sealed interface Success {
        data class Weather          (val data: WeatherResponseDomainModel)      : ApiResult
        data class Autocomplete     (val data: List<AutocompleteDomainModel>)   : ApiResult
    }
    data class Error        (val code: Int? = null, val message: String? = null)    : ApiResult
    data class Exception    (val e: Throwable? = null, val message: String? = null) : ApiResult
}
