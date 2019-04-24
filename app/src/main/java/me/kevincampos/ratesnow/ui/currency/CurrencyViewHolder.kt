package me.kevincampos.ratesnow.ui.currency

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import me.kevincampos.ratesnow.domain.model.CurrencyWithConversion


class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val currencyFlag: ImageView = itemView.findViewById(me.kevincampos.ratesnow.R.id.currency_flag)
    private val currencySymbol: TextView = itemView.findViewById(me.kevincampos.ratesnow.R.id.currency_symbol)
    private val currencyName: TextView = itemView.findViewById(me.kevincampos.ratesnow.R.id.currency_name)
    private val currencyInputValue: EditText = itemView.findViewById(me.kevincampos.ratesnow.R.id.currency_input_value)

    fun onBind(
        currencyItem: CurrencyWithConversion,
        onItemSelected: (CurrencyWithConversion, Float) -> Unit,
        onItemChange: (CurrencyWithConversion, Float) -> Unit
    ) {
        val context = itemView.context

        currencySymbol.text = currencyItem.symbol

        Glide.with(context)
            .load(Uri.parse("file:///android_asset/${currencyItem.countryShortCode.toLowerCase()}.png"))
            .into(currencyFlag)

        val dynamicWorkflowTypeRes =
            context.resources.getIdentifier("CURRENCY_${currencyItem.symbol}", "string", context.packageName)
        val strings = if (dynamicWorkflowTypeRes > 0) {
            context.resources.getString(dynamicWorkflowTypeRes)
        } else {
            null
        }
        currencyName.text = strings

        currencyInputValue.setText("%.2f".format(currencyItem.valueInConversion))
        currencyInputValue.isClickable = false
        currencyInputValue.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                onItemSelected(currencyItem, currencyInputValue.text.toString().replace(",", ".").toFloat())
                currencyInputValue.requestFocus()
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.showSoftInput(currencyInputValue, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        val oldWatcher = currencyInputValue.tag as CallbackTextWatcher?
        if (oldWatcher != null)
            currencyInputValue.removeTextChangedListener(oldWatcher)

        val newWatcher = CallbackTextWatcher {
            if (currencyInputValue.text.isNotEmpty()) {
                onItemChange(currencyItem, currencyInputValue.text.toString().replace(",", ".").toFloat())
            }
        }
        currencyInputValue.tag = newWatcher
        currencyInputValue.addTextChangedListener(newWatcher)

        itemView.setOnClickListener {
            onItemSelected(currencyItem, currencyInputValue.text.toString().replace(",", ".").toFloat())
            currencyInputValue.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.showSoftInput(currencyInputValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private inner class CallbackTextWatcher(
        private val onTextChange: () -> Unit
    ) : TextWatcher {

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            onTextChange()
        }

        override fun afterTextChanged(editable: Editable) {
        }
    }
}
