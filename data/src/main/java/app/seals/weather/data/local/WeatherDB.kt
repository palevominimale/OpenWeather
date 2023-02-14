package app.seals.weather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.seals.weather.data.models.AutocompleteDataModel
import app.seals.weather.data.models.WeatherResponseDataModel

@Database(entities = [WeatherResponseDataModel::class, AutocompleteDataModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDB : RoomDatabase() {

    abstract fun dao() : WeatherDao

    companion object {
        private var INSTANCE : WeatherDB? = null
        fun getInstance(context: Context) : WeatherDB? {
            if(INSTANCE == null) synchronized(WeatherDB::class) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    WeatherDB::class.java,
                "forecast.db"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}