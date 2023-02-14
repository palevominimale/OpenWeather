package app.seals.weather.data.local

import androidx.room.TypeConverter
import app.seals.weather.data.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun forecastDataModelToString(forecast: ForecastDataModel) = gson.toJson(forecast)
    @TypeConverter
    fun weatherDataModelToString(weather: WeatherResponseDataModel) = gson.toJson(weather)
    @TypeConverter
    fun hourDataModelToString(hour: HourDataModel) = gson.toJson(hour)
    @TypeConverter
    fun forecastDayDataModelToString(forecastday: ForecastDayDataModel) = gson.toJson(forecastday)
    @TypeConverter
    fun dayDataModelTostring(day: DayDataModel) = gson.toJson(day)
    @TypeConverter
    fun currentDataModelTostring(current: CurrentWeatherDataModel) = gson.toJson(current)
    @TypeConverter
    fun conditionDataModelTostring(condition: ConditionDataModel) = gson.toJson(condition)
    @TypeConverter
    fun aqiDataModelTostring(aqiDataModel: AqiDataModel) = gson.toJson(aqiDataModel)
    @TypeConverter
    fun astroDataModelTostring(astroDataModel: AstroDataModel) = gson.toJson(astroDataModel)
    @TypeConverter
    fun alertsDataModelTostring(alertsDataModel: AlertsDataModel) = gson.toJson(alertsDataModel)
    @TypeConverter
    fun alertDataModelTostring(alertDataModel: AlertDataModel) = gson.toJson(alertDataModel)
    @TypeConverter
    fun locationDataModelTostring(locationDataModel: LocationDataModel) = gson.toJson(locationDataModel)

    @TypeConverter
    fun stringToForecastDataModel(string: String) : ForecastDataModel {
        return gson.fromJson(string, object : TypeToken<ForecastDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToWeatherDataModel(string: String) : WeatherResponseDataModel {
        return gson.fromJson(string, object : TypeToken<WeatherResponseDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToHourDataModel(string: String) : HourDataModel {
        return gson.fromJson(string, object : TypeToken<HourDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToForecastDayDataModel(string: String) : ForecastDayDataModel {
        return gson.fromJson(string, object : TypeToken<ForecastDayDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToCurrentDataModel(string: String) : CurrentWeatherDataModel {
        return gson.fromJson(string, object : TypeToken<CurrentWeatherDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToConditionDataModel(string: String) : ConditionDataModel {
        return gson.fromJson(string, object : TypeToken<ConditionDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToAqiDataModel(string: String) : AqiDataModel {
        return gson.fromJson(string, object : TypeToken<AqiDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToAstroDataModel(string: String) : AstroDataModel {
        return gson.fromJson(string, object : TypeToken<AstroDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToAlertsDataModel(string: String) : AlertsDataModel {
        return gson.fromJson(string, object : TypeToken<AlertsDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToAlertDataModel(string: String) : AlertDataModel {
        return gson.fromJson(string, object : TypeToken<AlertDataModel>(){}.type)
    }
    @TypeConverter
    fun stringToLocationDataModel(string: String) : LocationDataModel {
        return gson.fromJson(string, object : TypeToken<LocationDataModel>(){}.type)
    }
}