package me.kevincampos.ratesnow.domain.repository

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.ExchangeRate

interface ExchangeRateRepository {

    suspend fun getExchangeRates(mustUseCache: Boolean): Result<List<ExchangeRate>>

    suspend fun insertExchangeRates(exchangeRates: List<ExchangeRate>): Result<Boolean>

}