package com.aveyon.meivsm.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AppDao {
    @Insert
    suspend fun insertContacts(vararg contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContracts(vararg contract: Contract)

    @Insert
    suspend fun insertEOA(vararg eoa: ExternallyOwnedAccount)

    @Update
    suspend fun updateContacts(vararg contact: Contact)

    @Update
    suspend fun updateContracts(vararg contract: Contract)

    @Update
    suspend fun updateEOA(vararg eoa: ExternallyOwnedAccount)

    @Delete
    suspend fun deleteContacts(vararg contact: Contact)

    @Delete
    suspend fun deleteContracts(vararg contract: Contract)

    @Delete
    suspend fun deleteEOA(vararg eoa: ExternallyOwnedAccount)

    @Query("DELETE FROM contacts")
    suspend fun deleteAllContacts()

    @Query("DELETE FROM eoas")
    suspend fun deleteAllEOAs()

    @Query("SELECT * FROM eoas")
    fun monitorAllEOAs(): LiveData<List<ExternallyOwnedAccount>>

    @Query("SELECT * FROM eoas")
    suspend fun loadAllEOAs(): List<ExternallyOwnedAccount>

    @Query("SELECT * FROM contacts")
    suspend fun loadAllContacts(): List<Contact>

    @Query("SELECT * FROM contacts")
    fun monitorAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contracts")
    fun monitorAllContracts(): LiveData<List<Contract>>

    @Query("SELECT * FROM contracts WHERE eoaId = :id")
    fun monitorAllContractsById(id: Int): LiveData<List<Contract>>
}