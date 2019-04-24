package me.kevincampos.ratesnow.domain.model

class CurrencyWithConversion(
    val symbol: String,
    val inputValue: Float,
    val lastSelectionTimestamp: Long,
    val decimalPointCharacter: String,
    val thousandsSeparatorCharacter: String,
    val countryShortCode: String,
    val valueInConversion: Float
)