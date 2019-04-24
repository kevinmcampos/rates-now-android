package me.kevincampos.ratesnow.remote.model

import com.google.gson.annotations.SerializedName

class CurrencyResponse (
    val code: String,
    @SerializedName("decimal_point") val decimalPoint: String,
    @SerializedName("thousands_separator") val thousandsSeparator: String,
    @SerializedName("default_country") val defaultCountry: String
)
