package me.kevincampos.ratesnow.data.datasource

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.Currency

interface CurrencyLocalDataSource {

    suspend fun getCurrencies(): Result<List<Currency>>

    suspend fun insertCurrencies(currencies: List<Currency>): Result<Boolean>

    suspend fun updateCurrency(symbol: String, inputValue: Float, lastSelectionTimestamp: Long): Result<Boolean>

}