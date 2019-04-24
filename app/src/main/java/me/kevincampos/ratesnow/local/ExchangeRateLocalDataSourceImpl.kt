package me.kevincampos.ratesnow.local

import me.kevincampos.ratesnow.local.database.RatesNowDatabase
import me.kevincampos.ratesnow.local.mapper.ExchangeRateEntityMapper
import me.kevincampos.ratesnow.data.datasource.ExchangeRateLocalDataSource
import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.ExchangeRate
import javax.inject.Inject

class ExchangeRateLocalDataSourceImpl @Inject constructor(
    private val database: RatesNowDatabase,
    private val mapper: ExchangeRateEntityMapper
) : ExchangeRateLocalDataSource {

    override suspend fun getExchangeRates(): Result<List<ExchangeRate>> {
        val currencies = database.exchangeRateDao().getExchangeRates()
            .map {
                mapper.mapFromEntity(it)
            }
        return Result.Success(currencies)
    }

    override suspend fun insertExchangeRates(exchangeRates: List<ExchangeRate>): Result<Boolean> {
        database.exchangeRateDao().insertExchangeRates(
            exchangeRates.map { mapper.mapToEntity(it) }
        )
        return Result.Success(true)
    }

}