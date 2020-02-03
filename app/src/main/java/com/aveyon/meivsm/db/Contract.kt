package com.aveyon.meivsm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contracts")
data class Contract(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var publicAddress: String,
    var value: Int,
    // Foreign Key. eoa = externally owned account
    var eoaId: Int
)