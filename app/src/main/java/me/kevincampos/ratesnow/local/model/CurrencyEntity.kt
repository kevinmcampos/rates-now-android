package me.kevincampos.ratesnow.local.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import me.kevincampos.ratesnow.local.database.CurrencyConstants

@Entity(tableName = CurrencyConstants.TABLE_NAME)
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = CurrencyConstants.COLUMN_SYMBOL) var symbol: String,
    @ColumnInfo(name = CurrencyConstants.COLUMN_INPUT_VALUE) var inputValue: Float,
    @ColumnInfo(name = CurrencyConstants.COLUMN_LAST_SELECTION) var lastSelectionTimestamp: Long,
    var decimalPointCharacter: String,
    var thousandsSeparatorCharacter: String,
    var countryShortCode: String
)