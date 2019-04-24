package me.kevincampos.ratesnow.remote

import me.kevincampos.ratesnow.data.datasource.CurrencyRemoteDataSource
import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.Currency
import me.kevincampos.ratesnow.remote.mapper.CurrencyResponseMapper
import me.kevincampos.ratesnow.remote.service.CurrencyService
import me.kevincampos.ratesnow.util.safeApiCall
import java.io.IOException
import javax.inject.Inject

class CurrencyRemoteDataSourceImpl @Inject constructor(
    private val service: CurrencyService,
    private val mapper: CurrencyResponseMapper
) : CurrencyRemoteDataSource {

    override suspend fun getCurrencies(): Result<List<Currency>> = safeApiCall(
        call = { requestGetCurrencies() },
        errorMessage = "Unable to get currencies from remote"
    )

    private suspend fun requestGetCurrencies(): Result<List<Currency>> {
        val response = service.getCurrencies().await()
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) {
                Result.Error(IOException("Empty data ${response.code()} ${response.message()}"))
            } else {
                val map = body.map { mapper.mapToDomain(it) }
                Result.Success(map)
            }
        } else {
            Result.Error(IOException("Error getting data ${response.code()} ${response.message()}"))
        }
    }

}