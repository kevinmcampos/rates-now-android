package me.kevincampos.ratesnow.data

import me.kevincampos.ratesnow.data.datasource.ExchangeRateLocalDataSource
import me.kevincampos.ratesnow.data.datasource.ExchangeRateRemoteDataSource
import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.ExchangeRate
import me.kevincampos.ratesnow.domain.repository.ExchangeRateRepository
import java.io.IOException
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource,
    private val exchangeRateLocalDataSource: ExchangeRateLocalDataSource
) : ExchangeRateRepository {

    override suspend fun getExchangeRates(mustUseCache: Boolean): Result<List<ExchangeRate>> {
        if (mustUseCache) {
            val localResult = exchangeRateLocalDataSource.getExchangeRates()
            if (localResult is Result.Success && localResult.data.isEmpty()) {
                val remoteResult = exchangeRateRemoteDataSource.getExchangeRates()
                if (remoteResult is Result.Success) {
                    insertExchangeRates(remoteResult.data)
                    return exchangeRateLocalDataSource.getExchangeRates()
                } else {
                    return Result.Error(IOException("Failed to get exchange rates from remote"))
                }
            } else {
                return localResult
            }
        } else {
            return exchangeRateRemoteDataSource.getExchangeRates()
        }
    }

    override suspend fun insertExchangeRates(exchangeRates: List<ExchangeRate>): Result<Boolean> {
        return exchangeRateLocalDataSource.insertExchangeRates(exchangeRates)
    }

}