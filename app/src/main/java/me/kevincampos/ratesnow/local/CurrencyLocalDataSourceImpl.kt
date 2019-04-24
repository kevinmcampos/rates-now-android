package me.kevincampos.ratesnow.local

import me.kevincampos.ratesnow.data.datasource.CurrencyLocalDataSource
import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.Currency
import me.kevincampos.ratesnow.local.database.RatesNowDatabase
import me.kevincampos.ratesnow.local.mapper.CurrencyEntityMapper
import javax.inject.Inject

class CurrencyLocalDataSourceImpl @Inject constructor(
    private val database: RatesNowDatabase,
    private val mapper: CurrencyEntityMapper
) : CurrencyLocalDataSource {

    override suspend fun getCurrencies(): Result<List<Currency>> {
        val currencies = database.currencyDao().getCurrencies()
            .map {
                mapper.mapFromEntity(it)
            }
        return Result.Success(currencies)
    }

    override suspend fun insertCurrencies(currencies: List<Currency>): Result<Boolean> {
        database.currencyDao().insertCurrencies(
            currencies.map { mapper.mapToEntity(it) }
        )
        return Result.Success(true)
    }

    override suspend fun updateCurrency(
        symbol: String,
        inputValue: Float,
        lastSelectionTimestamp: Long
    ): Result<Boolean> {
        database.currencyDao().updateLastSelectionAndInputValue(symbol, inputValue, lastSelectionTimestamp)
        return Result.Success(true)
    }

}