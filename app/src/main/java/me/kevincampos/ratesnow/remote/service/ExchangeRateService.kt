package me.kevincampos.ratesnow.remote.service

import kotlinx.coroutines.Deferred
import me.kevincampos.ratesnow.remote.model.RateResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateService {

    @GET("latest")
    fun getLastExchangeRates(@Query("base") baseSymbol: String): Deferred<Response<RatesResponse>>

    class RatesResponse(val rates: List<RateResponse>)

}