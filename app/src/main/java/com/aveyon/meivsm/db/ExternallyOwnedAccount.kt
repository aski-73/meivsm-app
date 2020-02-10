package com.aveyon.meivsm.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "eoas")
data class ExternallyOwnedAccount(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    override var name: String = "",
    override var publicAddress: String = "",
    var privateKey: String = ""
) : AccountInterface {
    override fun toString(): String {
        return name
    }
}