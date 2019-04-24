package me.kevincampos.ratesnow.domain.interactor

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.repository.CurrencyRepository
import javax.inject.Inject

class SetSelectedCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository
) {

    suspend operator fun invoke(symbol: String, inputValue: Float): Result<Boolean> {
        return repository.updateCurrency(symbol, inputValue, System.currentTimeMillis())
    }

}