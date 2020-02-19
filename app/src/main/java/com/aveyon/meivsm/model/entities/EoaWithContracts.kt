package com.aveyon.meivsm.model.entities

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Data class that represent the One To Many Relationship between an EOA (Parent) and
 * contracts (children)
 */
data class EoaWithContracts(

    @Embedded var eoa: ExternallyOwnedAccount,


    @Relation(
        parentColumn = "id",
        entityColumn = "eoaId"
    )
    var contracts: List<Contract>
)