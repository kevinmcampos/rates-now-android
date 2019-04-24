package me.kevincampos.ratesnow.ui.currency

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.kevincampos.ratesnow.domain.model.CurrencyWithConversion


class CurrencyAdapter(
    private val onItemSelected: (CurrencyWithConversion, Float) -> Unit,
    private val onItemChange: (CurrencyWithConversion, Float) -> Unit
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    private var currencies = arrayListOf<CurrencyWithConversion>()

    fun swap(newCurrencies: List<CurrencyWithConversion>) {
        val calculateDiff = DiffUtil.calculateDiff(CurrencyDiffCallback(currencies.toList(), newCurrencies))
        calculateDiff.dispatchUpdatesTo(this)

        this.currencies.clear()
        this.currencies.addAll(newCurrencies)
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    override fun onCreateViewHolder(parentView: ViewGroup, position: Int): CurrencyViewHolder {
        val itemView = LayoutInflater.from(parentView.context)
            .inflate(me.kevincampos.ratesnow.R.layout.item_currency, parentView, false)
        return CurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: CurrencyViewHolder, position: Int) {
        viewHolder.onBind(currencies[position], onItemSelected, onItemChange)

    }

}
