package me.kevincampos.ratesnow.ui.injection.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.kevincampos.ratesnow.BuildConfig
import me.kevincampos.ratesnow.data.datasource.CurrencyRemoteDataSource
import me.kevincampos.ratesnow.data.datasource.ExchangeRateRemoteDataSource
import me.kevincampos.ratesnow.remote.CurrencyRemoteDataSourceImpl
import me.kevincampos.ratesnow.remote.ExchangeRateRemoteDataSourceImpl
import me.kevincampos.ratesnow.remote.service.CurrencyService
import me.kevincampos.ratesnow.remote.service.CurrencyServiceFactory
import me.kevincampos.ratesnow.remote.service.ExchangeRateService
import me.kevincampos.ratesnow.remote.service.ExchangeRateServiceFactory

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideExchangeRateService(): ExchangeRateService {
            return ExchangeRateServiceFactory.makeExchangeRateService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun provideCurrencyService(): CurrencyService {
            return CurrencyServiceFactory.makeCurrencyService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindExchangeRateRemoteDataSource(projectsRemote: ExchangeRateRemoteDataSourceImpl): ExchangeRateRemoteDataSource

    @Binds
    abstract fun bindCurrencyRemoteDataSource(projectsRemote: CurrencyRemoteDataSourceImpl): CurrencyRemoteDataSource
}