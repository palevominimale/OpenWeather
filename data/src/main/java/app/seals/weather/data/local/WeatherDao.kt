package app.seals.weather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.seals.weather.data.models.AutocompleteDataModel
import app.seals.weather.data.models.WeatherResponseDataModel

@Dao
interface WeatherDao {

    @Query("DELETE FROM forecast")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(forecast: WeatherResponseDataModel)

    @Query("select * from forecast where id=:id limit 1")
    fun load(id: String) : WeatherResponseDataModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLocation(location: AutocompleteDataModel)

    @Query("delete from locations where id=(:id)")
    fun deleteLocation(id: Int)

    @Query("select * from locations")
    fun getLocations() : List<AutocompleteDataModel>

}