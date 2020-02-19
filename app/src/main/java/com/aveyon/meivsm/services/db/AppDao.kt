package com.aveyon.meivsm.services.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aveyon.meivsm.model.entities.Contact
import com.aveyon.meivsm.model.entities.ExternallyOwnedAccount


@Dao
interface AppDao {
    @Insert
    suspend fun insertContacts(vararg contact: Contact)

    @Insert
    suspend fun insertEOA(vararg eoa: ExternallyOwnedAccount)

    @Update
    suspend fun updateContacts(vararg contact: Contact)

    @Update
    suspend fun updateEOA(vararg eoa: ExternallyOwnedAccount)

    @Delete
    suspend fun deleteContacts(vararg contact: Contact)

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
}