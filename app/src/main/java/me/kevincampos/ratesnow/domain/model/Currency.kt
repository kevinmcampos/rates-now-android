package me.kevincampos.ratesnow.domain.model

class Currency(
    val symbol: String,
    val inputValue: Float,
    val lastSelectionTimestamp: Long,
    val decimalPointCharacter: String,
    val thousandsSeparatorCharacter: String,
    val countryShortCode: String // A2 ISO Standard
)