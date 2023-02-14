package app.seals.weather.ui.screens

import android.content.Context
import app.seals.weather.R
import app.seals.weather.data.models.LanguagesResponseDataModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConditionSelector(
    context: Context,
    private val langIso: String
) {
    private val stream = context.assets.open("langs.json")
    private val size = stream.available()
    private val buffer = ByteArray(size)
    init {
        stream.read(buffer)
        stream.close()
    }
    private val json = String(buffer, Charsets.UTF_8)
    private val typeToken = object : TypeToken<List<LanguagesResponseDataModel>>() {}.type
    private val model = Gson().fromJson<List<LanguagesResponseDataModel>>(json, typeToken)

    fun descr(code: Int, day: Boolean) : String {
        return try {
            val description = model.first { it.code == code }.languages.first { it.langIso == langIso }
            if(day) description.dayText ?: "no data $code $day" else description.nightText ?: "no data $code $day"
        } catch (e: Throwable) {
            return "exc.; no data $code $day"
        }
    }
}

fun icon(code: Int, day: Boolean) : Int {
    return if(day) when(code) {
        1000 -> R.drawable.wi_day_sunny
        1003 -> R.drawable.wi_day_sunny_overcast
        1006 -> R.drawable.wi_day_cloudy
        1009 -> R.drawable.wi_day_sunny_overcast
        1030 -> R.drawable.wi_day_fog
        1063 -> R.drawable.wi_day_showers
        1066 -> R.drawable.wi_day_snow
        1069 -> R.drawable.wi_day_sleet
        1072 -> R.drawable.wi_day_rain_mix
        1087 -> R.drawable.wi_day_thunderstorm
        1114 -> R.drawable.wi_day_snow_wind
        1117 -> R.drawable.wi_day_snow_thunderstorm
        1135 -> R.drawable.wi_day_fog
        1147 -> R.drawable.wi_day_fog
        1150 -> R.drawable.wi_day_showers
        1153 -> R.drawable.wi_day_showers
        1168 -> R.drawable.wi_day_rain_mix
        1171 -> R.drawable.wi_day_rain_mix
        1180 -> R.drawable.wi_day_rain
        1183 -> R.drawable.wi_day_rain
        1186 -> R.drawable.wi_day_rain
        1189 -> R.drawable.wi_day_rain
        1192 -> R.drawable.wi_day_rain_wind
        1195 -> R.drawable.wi_day_rain_wind
        1198 -> R.drawable.wi_day_rain
        1201 -> R.drawable.wi_day_rain
        1204 -> R.drawable.wi_day_sleet
        1207 -> R.drawable.wi_day_sleet
        1210 -> R.drawable.wi_day_snow
        1213 -> R.drawable.wi_day_snow
        1216 -> R.drawable.wi_day_snow
        1219 -> R.drawable.wi_day_snow
        1222 -> R.drawable.wi_day_snow_wind
        1225 -> R.drawable.wi_day_snow_wind
        1237 -> R.drawable.wi_snowflake_cold
        1240 -> R.drawable.wi_day_showers
        1243 -> R.drawable.wi_day_showers
        1246 -> R.drawable.wi_day_showers
        1249 -> R.drawable.wi_day_sleet
        1252 -> R.drawable.wi_day_sleet
        1255 -> R.drawable.wi_day_sleet
        1258 -> R.drawable.wi_day_snow
        1261 -> R.drawable.wi_day_snow
        1264 -> R.drawable.wi_day_snow
        1273 -> R.drawable.wi_day_thunderstorm
        1276 -> R.drawable.wi_day_thunderstorm
        1279 -> R.drawable.wi_day_snow_thunderstorm
        1282 -> R.drawable.wi_day_snow_thunderstorm
        else -> R.drawable.wi_meteor
    } else {
        when (code) {
            1000 -> R.drawable.wi_night_clear
            1003 -> R.drawable.wi_night_cloudy
            1006 -> R.drawable.wi_night_cloudy
            1009 -> R.drawable.wi_night_cloudy
            1030 -> R.drawable.wi_night_fog
            1063 -> R.drawable.wi_night_showers
            1066 -> R.drawable.wi_night_snow
            1069 -> R.drawable.wi_night_sleet
            1072 -> R.drawable.wi_night_rain_mix
            1087 -> R.drawable.wi_night_thunderstorm
            1114 -> R.drawable.wi_night_snow_wind
            1117 -> R.drawable.wi_night_snow_thunderstorm
            1135 -> R.drawable.wi_night_fog
            1147 -> R.drawable.wi_night_fog
            1150 -> R.drawable.wi_night_showers
            1153 -> R.drawable.wi_night_showers
            1168 -> R.drawable.wi_night_rain_mix
            1171 -> R.drawable.wi_night_rain_mix
            1180 -> R.drawable.wi_night_rain
            1183 -> R.drawable.wi_night_rain
            1186 -> R.drawable.wi_night_rain
            1189 -> R.drawable.wi_night_rain
            1192 -> R.drawable.wi_night_rain_wind
            1195 -> R.drawable.wi_night_rain_wind
            1198 -> R.drawable.wi_night_rain
            1201 -> R.drawable.wi_night_rain
            1204 -> R.drawable.wi_night_sleet
            1207 -> R.drawable.wi_night_sleet
            1210 -> R.drawable.wi_night_snow
            1213 -> R.drawable.wi_night_snow
            1216 -> R.drawable.wi_night_snow
            1219 -> R.drawable.wi_night_snow
            1222 -> R.drawable.wi_night_snow_wind
            1225 -> R.drawable.wi_night_snow_wind
            1237 -> R.drawable.wi_snowflake_cold
            1240 -> R.drawable.wi_night_showers
            1243 -> R.drawable.wi_night_showers
            1246 -> R.drawable.wi_night_showers
            1249 -> R.drawable.wi_night_sleet
            1252 -> R.drawable.wi_night_sleet
            1255 -> R.drawable.wi_night_sleet
            1258 -> R.drawable.wi_night_snow
            1261 -> R.drawable.wi_night_snow
            1264 -> R.drawable.wi_night_snow
            1273 -> R.drawable.wi_night_thunderstorm
            1276 -> R.drawable.wi_night_thunderstorm
            1279 -> R.drawable.wi_night_snow_thunderstorm
            1282 -> R.drawable.wi_night_snow_thunderstorm
            else -> R.drawable.wi_meteor
        }
    }
}