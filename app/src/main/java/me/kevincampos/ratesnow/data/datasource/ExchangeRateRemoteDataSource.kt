package me.kevincampos.ratesnow.data.datasource

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.ExchangeRate

interface ExchangeRateRemoteDataSource {

    suspend fun getExchangeRates(): Result<List<ExchangeRate>>

}