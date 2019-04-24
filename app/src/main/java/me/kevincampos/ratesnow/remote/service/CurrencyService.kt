package me.kevincampos.ratesnow.remote.service

import kotlinx.coroutines.Deferred
import me.kevincampos.ratesnow.remote.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyService {

    @GET("kevinmcampos/39b7a550bec3f432641f517ca05604e7/raw/01b04ed9dd57162617ba920801f175919889902d/currencies.json")
    fun getCurrencies(): Deferred<Response<List<CurrencyResponse>>>

}