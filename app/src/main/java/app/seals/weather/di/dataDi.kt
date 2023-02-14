package app.seals.weather.di

import app.seals.weather.data.api.WeatherApiImpl
import app.seals.weather.data.local.LocalRepoImpl
import app.seals.weather.data.location.LocationProviderImpl
import app.seals.weather.data.prefs.PrefsProviderImpl
import app.seals.weather.domain.LocationProvider
import app.seals.weather.domain.WeatherApi
import app.seals.weather.domain.models.PrefsProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataDi = module {
    single <WeatherApi> {
        WeatherApiImpl(
            weatherDB = get()
        )
    }

    single <LocationProvider> {
        LocationProviderImpl(androidContext())
    }

    single {
        LocalRepoImpl(androidContext())
    }

    single <PrefsProvider> {
        PrefsProviderImpl(androidContext())
    }
}