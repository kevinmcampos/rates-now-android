package me.kevincampos.ratesnow.ui.currency

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_converter.*
import me.kevincampos.ratesnow.R
import me.kevincampos.ratesnow.domain.model.CurrencyWithConversion
import me.kevincampos.ratesnow.presentation.ConversionsViewModel
import me.kevincampos.ratesnow.presentation.state.Resource
import me.kevincampos.ratesnow.presentation.state.ResourceState
import me.kevincampos.ratesnow.ui.injection.ViewModelFactory
import javax.inject.Inject


class ConversionsActivity : AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModelFactory<ConversionsViewModel>

//    @Inject
//    lateinit var currencyAdapter: CurrencyAdapter

    lateinit var viewModel: ConversionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
        AndroidInjection.inject(this)

        viewModel = ViewModelProviders.of(this, vmFactory)[ConversionsViewModel::class.java]

        conversion_list.adapter = CurrencyAdapter({ currency, value ->
            conversion_list.layoutManager?.scrollToPosition(0)
            viewModel.selectedCurrencyChanged(currency.symbol, value)
        }, { currency, value ->
            viewModel.inputValueChanged(currency.symbol, value)
        })
        conversion_list.setOnTouchListener { v, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(v.windowToken, 0)
        }
        (conversion_list.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
        conversion_list.layoutManager = LinearLayoutManager(this)

        viewModel.getCurrencies().observe(this,
            Observer<Resource<List<CurrencyWithConversion>>> {
                it?.let {
                    handleDataState(it)
                }
            })
    }

    private fun handleDataState(resource: Resource<List<CurrencyWithConversion>>) {
        when (resource.status) {
            ResourceState.SUCCESS -> {
                setupScreenForSuccess(resource.data)
            }
            ResourceState.LOADING -> {
                progress.visibility = View.VISIBLE
                conversion_list.visibility = View.GONE
                error_message.visibility = View.GONE
            }
            ResourceState.ERROR -> {
                progress.visibility = View.GONE
                conversion_list.visibility = View.GONE
                error_message.visibility = View.VISIBLE
                error_message.text = resource.message
            }
        }
    }

    private fun setupScreenForSuccess(currencies: List<CurrencyWithConversion>?) {
        progress.visibility = View.GONE
        error_message.visibility = View.GONE
        currencies?.let {
            val adapter = conversion_list.adapter as CurrencyAdapter
            adapter.swap(it)
            conversion_list.visibility = View.VISIBLE
        }
    }

}
