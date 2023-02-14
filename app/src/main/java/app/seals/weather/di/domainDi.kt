package app.seals.weather.di

import app.seals.weather.data.local.LocalRepoImpl
import app.seals.weather.domain.LocalRepo
import org.koin.dsl.module

val domainDi = module {
    single <LocalRepo> {
        get(LocalRepoImpl::class)
    }
}