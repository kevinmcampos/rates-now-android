package me.kevincampos.ratesnow.data

import me.kevincampos.ratesnow.data.datasource.CurrencyLocalDataSource
import me.kevincampos.ratesnow.data.datasource.CurrencyRemoteDataSource
import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.Currency
import me.kevincampos.ratesnow.domain.repository.CurrencyRepository
import java.io.IOException
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyRemoteDataSource: CurrencyRemoteDataSource,
    private val currencyLocalDataSource: CurrencyLocalDataSource
) : CurrencyRepository {

    override suspend fun getCurrencies(): Result<List<Currency>> {
        val localResult = currencyLocalDataSource.getCurrencies()
        if (localResult is Result.Success && localResult.data.isEmpty()) {
            val remoteResult = currencyRemoteDataSource.getCurrencies()
            if (remoteResult is Result.Success) {
                insertCurrencies(remoteResult.data)
                return currencyLocalDataSource.getCurrencies()
            } else {
                return Result.Error(IOException("Failed to get currencies from remote"))
            }
        } else {
            return localResult
        }
    }

    override suspend fun insertCurrencies(currencies: List<Currency>): Result<Boolean> {
        return currencyLocalDataSource.insertCurrencies(currencies)
    }

    override suspend fun updateCurrency(
        symbol: String,
        inputValue: Float,
        lastSelectionTimestamp: Long
    ): Result<Boolean> {
        return currencyLocalDataSource.updateCurrency(symbol, inputValue, lastSelectionTimestamp)
    }

}