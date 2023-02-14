package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class AlertsDataModel(
    @SerializedName("alert" ) val alert : ArrayList<AlertDataModel> = arrayListOf()
)
