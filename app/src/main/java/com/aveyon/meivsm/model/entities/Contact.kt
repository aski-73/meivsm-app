package com.aveyon.meivsm.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aveyon.meivsm.services.db.AccountInterface

@Entity(tableName = "contacts")
data class Contact (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    override var name: String = "",
    override var publicAddress: String = ""
) : AccountInterface {
    override fun toString(): String {
        return name
    }
}
