package me.kevincampos.ratesnow.data.datasource

import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.model.Currency

interface CurrencyRemoteDataSource {

    suspend fun getCurrencies(): Result<List<Currency>>

}
