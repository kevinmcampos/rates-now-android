package me.kevincampos.ratesnow.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import me.kevincampos.ratesnow.local.database.CurrencyConstants.COLUMN_INPUT_VALUE
import me.kevincampos.ratesnow.local.database.CurrencyConstants.COLUMN_LAST_SELECTION
import me.kevincampos.ratesnow.local.database.CurrencyConstants.COLUMN_SYMBOL
import me.kevincampos.ratesnow.local.database.CurrencyConstants.TABLE_NAME
import me.kevincampos.ratesnow.local.model.CurrencyEntity

@Dao
abstract class CurrencyDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_LAST_SELECTION DESC")
    @JvmSuppressWildcards
    abstract fun getCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    abstract fun insertCurrencies(currencyEntities: List<CurrencyEntity>)

    @Query("UPDATE $TABLE_NAME SET $COLUMN_LAST_SELECTION = :lastSelection, $COLUMN_INPUT_VALUE = :inputValue WHERE $COLUMN_SYMBOL = :symbol")
    abstract fun updateLastSelectionAndInputValue(symbol: String, inputValue: Float, lastSelection: Long)

}