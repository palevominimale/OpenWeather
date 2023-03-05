package app.seals.weather.di

import android.content.Context
import app.seals.weather.data.api.WeatherApiImpl
import app.seals.weather.data.local.LocalRepoImpl
import app.seals.weather.data.prefs.PrefsProviderImpl
import app.seals.weather.domain.LocalRepo
import app.seals.weather.domain.WeatherApi
import app.seals.weather.domain.models.PrefsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesWeatherApi(impl: WeatherApiImpl) : WeatherApi = impl

    @Provides
    @Singleton
    fun providesLocalRepo(impl: LocalRepoImpl) : LocalRepo = impl

    @Provides
    @Singleton
    fun providesWeatherApiImpl(db: LocalRepoImpl) = WeatherApiImpl(db)

    @Provides
    @Singleton
    fun providesLocalRepoImpl(@ApplicationContext context: Context) = LocalRepoImpl(context)

    @Provides
    @Singleton
    fun providesPrefsRepo(impl: PrefsProviderImpl) : PrefsProvider = impl

    @Provides
    @Singleton
    fun providesPrefsRepoImpl(@ApplicationContext context: Context) = PrefsProviderImpl(context)
}