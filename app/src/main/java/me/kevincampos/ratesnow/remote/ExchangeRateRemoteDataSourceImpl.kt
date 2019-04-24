package me.kevincampos.ratesnow.remote

import me.kevincampos.ratesnow.data.datasource.ExchangeRateRemoteDataSource
import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.ExchangeRate
import me.kevincampos.ratesnow.remote.mapper.ExchangeRateResponseMapper
import me.kevincampos.ratesnow.remote.service.ExchangeRateService
import me.kevincampos.ratesnow.util.safeApiCall
import java.io.IOException
import javax.inject.Inject

class ExchangeRateRemoteDataSourceImpl @Inject constructor(
    private val service: ExchangeRateService,
    private val mapper: ExchangeRateResponseMapper
): ExchangeRateRemoteDataSource {

    override suspend fun getExchangeRates(): Result<List<ExchangeRate>> = safeApiCall(
        call = { requestGetExchangeRates() },
        errorMessage = "Unable to get exchange rates from remote"
    )

    private suspend  fun requestGetExchangeRates(): Result<List<ExchangeRate>> {
        val response = service.getLastExchangeRates("EUR").await()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.Error(IOException("Empty data ${response.code()} ${response.message()}"))
            } else {
                val map = body.rates.map { mapper.mapToDomain(it) }.toMutableList()
                val eurExchangeRate = ExchangeRate("EUR", 1f)

                map += eurExchangeRate
                Result.Success(map)
            }
        } else {
            Result.Error(IOException("Error getting data ${response.code()} ${response.message()}"))
        }
    }

}