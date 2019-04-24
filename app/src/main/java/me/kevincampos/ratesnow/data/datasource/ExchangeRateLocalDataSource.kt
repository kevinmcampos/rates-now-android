package me.kevincampos.ratesnow.data.datasource

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.ExchangeRate

interface ExchangeRateLocalDataSource {

    suspend fun getExchangeRates(): Result<List<ExchangeRate>>

    suspend fun insertExchangeRates(exchangeRates: List<ExchangeRate>): Result<Boolean>

}