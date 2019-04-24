package me.kevincampos.ratesnow.domain.interactor

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.CurrencyWithConversion
import me.kevincampos.ratesnow.domain.repository.CurrencyRepository
import me.kevincampos.ratesnow.domain.repository.ExchangeRateRepository
import javax.inject.Inject

class GetCurrenciesWithConversionUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) {

    suspend operator fun invoke(): Result<List<CurrencyWithConversion>> {
        val currenciesResult = currencyRepository.getCurrencies()
        val exchangeRatesResult = exchangeRateRepository.getExchangeRates(true)

        if (exchangeRatesResult is Result.Success && currenciesResult is Result.Success) {
            if (currenciesResult.data.isEmpty()) return Result.Success(emptyList())

            val currenciesThatHaveExchange = currenciesResult.data.filter { currency ->
                exchangeRatesResult.data.any { it.symbol == currency.symbol }
            }

            val selectedCurrency = currenciesThatHaveExchange.first()
            val selectedExchangeRate = exchangeRatesResult.data.find { it.symbol == selectedCurrency.symbol }
            val selectedCurrencyInEur = selectedCurrency.inputValue / selectedExchangeRate!!.exchangeRateInEuro

            val currenciesWithConversions = currenciesThatHaveExchange.map { currency ->
                val conversion: Float = if (currency.symbol == selectedCurrency.symbol) {
                    selectedCurrency.inputValue
                } else {
                    selectedCurrencyInEur * exchangeRatesResult.data.find { it.symbol == currency.symbol }!!.exchangeRateInEuro
                }
                CurrencyWithConversion(
                    currency.symbol,
                    currency.inputValue,
                    currency.lastSelectionTimestamp,
                    currency.decimalPointCharacter,
                    currency.thousandsSeparatorCharacter,
                    currency.countryShortCode,
                    conversion
                )
            }

            return Result.Success(currenciesWithConversions)
        }
        return Result.Error(IllegalAccessException())
    }

}