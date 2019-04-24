package me.kevincampos.ratesnow.domain.repository

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.Currency

interface CurrencyRepository {

    suspend fun getCurrencies(): Result<List<Currency>>

    suspend fun insertCurrencies(currencies: List<Currency>): Result<Boolean>

    suspend fun updateCurrency(symbol: String, inputValue: Float, lastSelectionTimestamp: Long): Result<Boolean>

}