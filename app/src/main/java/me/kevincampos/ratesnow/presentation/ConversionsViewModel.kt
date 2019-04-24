package me.kevincampos.ratesnow.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.kevincampos.ratesnow.domain.Result
import me.kevincampos.ratesnow.domain.interactor.FetchExchangeRateUseCase
import me.kevincampos.ratesnow.domain.interactor.GetCurrenciesWithConversionUseCase
import me.kevincampos.ratesnow.domain.interactor.SetSelectedCurrencyUseCase
import me.kevincampos.ratesnow.domain.model.CurrencyWithConversion
import me.kevincampos.ratesnow.presentation.state.Resource
import me.kevincampos.ratesnow.presentation.state.ResourceState
import me.kevincampos.ratesnow.util.exhaustive
import java.util.*
import javax.inject.Inject


class ConversionsViewModel @Inject constructor(
    private val fetchExchangeRate: FetchExchangeRateUseCase,
    private val getCurrenciesWithConversion: GetCurrenciesWithConversionUseCase,
    private val setSelectedCurrency: SetSelectedCurrencyUseCase
) : ViewModel() {

    companion object {
        private const val FETCH_EXCHANGE_RATES_INTERVAL_IN_MILLID = 2_000L
    }

    private val liveData: MutableLiveData<Resource<List<CurrencyWithConversion>>> = MutableLiveData()

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var lastSelectedSymbol: String? = null

    private val fetchExchangeRatesTimerTask: TimerTask = object : TimerTask() {
        override fun run() {
            uiScope.launch(Dispatchers.IO) {
                val fetchResult = fetchExchangeRate()
                if (fetchResult is Result.Success) {
                    refreshCurrencies()
                }
                // fail silent to provide a smooth experience
            }
        }
    }

    init {
        uiScope.launch(Dispatchers.IO) {
            liveData.postValue(Resource(ResourceState.LOADING, null, null))
            refreshCurrencies()
        }

        Timer("Fetch exchange rates as pooling", false)
            .scheduleAtFixedRate(fetchExchangeRatesTimerTask, 0, FETCH_EXCHANGE_RATES_INTERVAL_IN_MILLID)
    }

    fun getCurrencies(): MutableLiveData<Resource<List<CurrencyWithConversion>>> {
        return liveData
    }

    fun inputValueChanged(symbol: String, value: Float) {
        if (symbol == lastSelectedSymbol) {
            selectedCurrencyChanged(symbol, value)
        }
    }

    fun selectedCurrencyChanged(symbol: String, inputValue: Float) {
        lastSelectedSymbol = symbol

        uiScope.launch(Dispatchers.IO) {
            val result = setSelectedCurrency(symbol, inputValue)
            when (result) {
                is Result.Success -> liveData.postValue(Resource(ResourceState.SUCCESS, liveData.value?.data, null))
                is Result.Error -> liveData.postValue(Resource(ResourceState.ERROR, null, "Failed to select currency"))
            }.exhaustive

            refreshCurrencies()
        }
    }

    private suspend fun refreshCurrencies() {
        val result = getCurrenciesWithConversion()
        if (result is Result.Success) {
            liveData.postValue(Resource(ResourceState.SUCCESS, result.data, null))
        } else {
            liveData.postValue(Resource(ResourceState.ERROR, null, "Failed to refresh currencies"))
        }
    }

    override fun onCleared() {
        super.onCleared()
        fetchExchangeRatesTimerTask.cancel()
    }

}