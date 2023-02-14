package app.seals.weather.data.models

import com.google.gson.annotations.SerializedName

data class AlertDataModel(
    @SerializedName("headline"    ) val headline    : String? = null,
    @SerializedName("msgtype"     ) val msgtype     : String? = null,
    @SerializedName("severity"    ) val severity    : String? = null,
    @SerializedName("urgency"     ) val urgency     : String? = null,
    @SerializedName("areas"       ) val areas       : String? = null,
    @SerializedName("category"    ) val category    : String? = null,
    @SerializedName("certainty"   ) val certainty   : String? = null,
    @SerializedName("event"       ) val event       : String? = null,
    @SerializedName("note"        ) val note        : String? = null,
    @SerializedName("effective"   ) val effective   : String? = null,
    @SerializedName("expires"     ) val expires     : String? = null,
    @SerializedName("desc"        ) val desc        : String? = null,
    @SerializedName("instruction" ) val instruction : String? = null
)
