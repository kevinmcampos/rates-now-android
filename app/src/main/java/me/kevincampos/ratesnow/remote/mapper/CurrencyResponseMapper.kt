package me.kevincampos.ratesnow.remote.mapper

import me.kevincampos.ratesnow.domain.model.Currency
import me.kevincampos.ratesnow.remote.model.CurrencyResponse
import javax.inject.Inject

class CurrencyResponseMapper @Inject constructor():
    ResponseMapper<CurrencyResponse, Currency> {

    override fun mapToDomain(response: CurrencyResponse): Currency {
        return Currency(response.code, 0f, 0, response.decimalPoint,
            response.thousandsSeparator, response.defaultCountry)
    }

}