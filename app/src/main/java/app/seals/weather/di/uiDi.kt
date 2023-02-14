package app.seals.weather.di

import app.seals.weather.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDi = module {
    viewModel {
        MainViewModel(
            weatherApi = get(),
            localRepo = get(),
            prefs = get()
        )
    }
}