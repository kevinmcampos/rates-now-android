package me.kevincampos.ratesnow.remote.mapper

import me.kevincampos.ratesnow.domain.model.ExchangeRate
import me.kevincampos.ratesnow.remote.model.RateResponse
import javax.inject.Inject

class ExchangeRateResponseMapper @Inject constructor():
    ResponseMapper<RateResponse, ExchangeRate> {

    override fun mapToDomain(response: RateResponse): ExchangeRate {
        return ExchangeRate(response.symbol, response.value)
    }

}