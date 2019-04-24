package me.kevincampos.ratesnow.ui.currency

import android.support.v7.util.DiffUtil
import me.kevincampos.ratesnow.domain.model.CurrencyWithConversion

class CurrencyDiffCallback(
    private val oldList: List<CurrencyWithConversion>,
    private val newList: List<CurrencyWithConversion>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldListPosition: Int, newListPosition: Int): Boolean {
        return oldList[oldListPosition].symbol == newList[newListPosition].symbol
    }

    override fun areContentsTheSame(oldListPosition: Int, newListPosition: Int): Boolean {
        return oldList[oldListPosition].valueInConversion == newList[newListPosition].valueInConversion
    }

}
