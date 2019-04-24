package me.kevincampos.ratesnow.local.mapper

import me.kevincampos.ratesnow.local.model.CurrencyEntity
import me.kevincampos.ratesnow.domain.model.Currency
import javax.inject.Inject

class CurrencyEntityMapper @Inject constructor() :
    EntityMapper<CurrencyEntity, Currency> {

    override fun mapFromEntity(entity: CurrencyEntity): Currency {
        return Currency(
            entity.symbol, entity.inputValue, entity.lastSelectionTimestamp,
            entity.decimalPointCharacter, entity.thousandsSeparatorCharacter, entity.countryShortCode
        )
    }

    override fun mapToEntity(domain: Currency): CurrencyEntity {
        return CurrencyEntity(
            domain.symbol, domain.inputValue, domain.lastSelectionTimestamp,
            domain.decimalPointCharacter, domain.thousandsSeparatorCharacter, domain.countryShortCode
        )
    }

}