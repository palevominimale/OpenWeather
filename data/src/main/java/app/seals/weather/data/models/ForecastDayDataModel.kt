package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class ForecastDayDataModel(
    @SerializedName("date"       ) val date      : String?                  = null,
    @SerializedName("date_epoch" ) val dateEpoch : Int?                     = null,
    @SerializedName("day"        ) val day       : DayDataModel?            = DayDataModel(),
    @SerializedName("astro"      ) val astro     : AstroDataModel?          = AstroDataModel(),
    @SerializedName("hour"       ) val hour      : ArrayList<HourDataModel> = arrayListOf()
)