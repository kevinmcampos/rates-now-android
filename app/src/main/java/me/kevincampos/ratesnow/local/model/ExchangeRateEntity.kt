package me.kevincampos.ratesnow.local.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import me.kevincampos.ratesnow.local.database.ExchangeRateConstants

@Entity(tableName = ExchangeRateConstants.TABLE_NAME)
data class ExchangeRateEntity(
    @PrimaryKey
    var symbol: String,
    var exchangeRateInEuro: Float
)