package app.seals.weather.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "locations")
data class AutocompleteDataModel(
    @PrimaryKey @SerializedName("id"      ) var id      : Int     = 0,
    @SerializedName("name"    )             var name    : String? = null,
    @SerializedName("region"  )             var region  : String? = null,
    @SerializedName("country" )             var country : String? = null,
    @SerializedName("lat"     )             var lat     : Double? = null,
    @SerializedName("lon"     )             var lon     : Double? = null,
    @SerializedName("url"     )             var url     : String? = null
)