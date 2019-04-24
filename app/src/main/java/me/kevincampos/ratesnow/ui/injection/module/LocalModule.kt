package me.kevincampos.ratesnow.ui.injection.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.kevincampos.ratesnow.data.datasource.CurrencyLocalDataSource
import me.kevincampos.ratesnow.data.datasource.ExchangeRateLocalDataSource
import me.kevincampos.ratesnow.local.CurrencyLocalDataSourceImpl
import me.kevincampos.ratesnow.local.ExchangeRateLocalDataSourceImpl
import me.kevincampos.ratesnow.local.database.RatesNowDatabase

@Module
abstract class LocalModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDatabase(application: Application): RatesNowDatabase {
            return RatesNowDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindCurrencyLocalDataSource(localDataSource: CurrencyLocalDataSourceImpl): CurrencyLocalDataSource

    @Binds
    abstract fun bindExchangeRateLocalDataSource(localDataSource: ExchangeRateLocalDataSourceImpl): ExchangeRateLocalDataSource

}