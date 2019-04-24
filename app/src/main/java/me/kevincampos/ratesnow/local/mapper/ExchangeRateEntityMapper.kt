package me.kevincampos.ratesnow.local.mapper

import me.kevincampos.ratesnow.local.model.ExchangeRateEntity
import me.kevincampos.ratesnow.domain.model.ExchangeRate
import javax.inject.Inject

class ExchangeRateEntityMapper @Inject constructor() : EntityMapper<ExchangeRateEntity, ExchangeRate> {

    override fun mapFromEntity(entity: ExchangeRateEntity): ExchangeRate {
        return ExchangeRate(entity.symbol, entity.exchangeRateInEuro)
    }

    override fun mapToEntity(domain: ExchangeRate): ExchangeRateEntity {
        return ExchangeRateEntity(domain.symbol, domain.exchangeRateInEuro)
    }

}