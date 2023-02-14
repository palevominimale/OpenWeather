package app.seals.weather.data.api

import app.seals.weather.data.BuildConfig
import app.seals.weather.data.local.LocalRepoImpl
import app.seals.weather.data.mapToDomain
import app.seals.weather.data.models.AutocompleteDataModel
import app.seals.weather.data.models.WeatherResponseDataModel
import app.seals.weather.domain.WeatherApi
import app.seals.weather.domain.models.ApiResult
import kotlinx.coroutines.flow.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("UNCHECKED_CAST")
class WeatherApiImpl(
    private val weatherDB: LocalRepoImpl
) : RemoteApi, WeatherApi {

    private var retrofit = Retrofit.Builder()
        .baseUrl(RemoteApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RemoteApi::class.java)

    override val result: SharedFlow<ApiResult> get() = _result
    private val _result = MutableSharedFlow<ApiResult>()

    private suspend fun <T: Any> handleApi(
        call: suspend () -> Call<T>
    ) : ApiResult {
        return try {
            val response = call().execute()
            val body = response.body()
            if(response.isSuccessful && body != null) {
                when(body) {
                    is WeatherResponseDataModel -> {
                        val id = "${body.location?.lat},${body.location?.lon ?: 0}"
                        weatherDB.saveForecast(body.copy(id = id))
                        ApiResult.Success.Weather(body.mapToDomain())
                    }
                    is List<*> -> {
                        if(body.isNotEmpty() && body[0] is AutocompleteDataModel) {
                            ApiResult.Success.Autocomplete((body as List<AutocompleteDataModel>).mapToDomain())
                        } else ApiResult.Error(0, "unknown type")

                    }
                    else -> { ApiResult.Error(0, "unknown type") }
                }
            }
            else ApiResult.Error(response.code(), response.message())
        }
        catch (e: HttpException) { ApiResult.Error(e.code(), e.localizedMessage) }
        catch (e: Throwable) { ApiResult.Exception(e) }
    }

    override fun getForecast(key: String, days: Int, location: String) = retrofit.getForecast(key, days, location)
    override fun getAutocomplete(key: String, query: String) = retrofit.getAutocomplete(key, query)

    override suspend fun getWeather(id: String) {
        _result.emit(handleApi { getForecast(BuildConfig.API_KEY, 10, id) })
    }

    override suspend fun getAutocomplete(query: String) {
        _result.emit(handleApi { getAutocomplete(BuildConfig.API_KEY, query) })
    }

}