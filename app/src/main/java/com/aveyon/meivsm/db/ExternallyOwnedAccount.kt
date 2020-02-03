package com.aveyon.meivsm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eoas")
data class ExternallyOwnedAccount(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var publicAddress: String = "",
    var privateKey: String = ""
)