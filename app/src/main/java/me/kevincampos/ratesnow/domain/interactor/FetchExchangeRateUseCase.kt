package me.kevincampos.ratesnow.domain.interactor

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.repository.ExchangeRateRepository
import java.io.IOException
import javax.inject.Inject

class FetchExchangeRateUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository
) {

    suspend operator fun invoke(): Result<Boolean> {
        val result = exchangeRateRepository.getExchangeRates(false)
        if (result is Result.Success) {
            exchangeRateRepository.insertExchangeRates(result.data)
            return Result.Success(true)
        }
        return Result.Error(IOException("Failed to fetch exchange rates from remote"))
    }

}