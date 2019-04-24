package me.kevincampos.ratesnow.ui.injection.module

import dagger.Binds
import dagger.Module
import me.kevincampos.ratesnow.data.CurrencyRepositoryImpl
import me.kevincampos.ratesnow.data.ExchangeRateRepositoryImpl
import me.kevincampos.ratesnow.domain.repository.CurrencyRepository
import me.kevincampos.ratesnow.domain.repository.ExchangeRateRepository

@Module
abstract class DataModule {

    @Binds
    abstract fun bindCurrencyRepository(repository: CurrencyRepositoryImpl): CurrencyRepository

    @Binds
    abstract fun bindExchangeRateRepository(repository: ExchangeRateRepositoryImpl): ExchangeRateRepository

}