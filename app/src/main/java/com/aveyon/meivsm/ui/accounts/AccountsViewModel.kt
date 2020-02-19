package com.aveyon.meivsm.ui.accounts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.aveyon.meivsm.services.db.*
import com.aveyon.meivsm.model.entities.Contact
import com.aveyon.meivsm.model.entities.ExternallyOwnedAccount
import com.aveyon.meivsm.services.ServiceLocator
import com.aveyon.meivsm.utils.exceptions.InvalidContactException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.web3j.utils.Convert
import java.math.MathContext.DECIMAL32

class AccountsViewModel(appplication: Application) : AndroidViewModel(appplication) {

    val accounts: LiveData<List<ExternallyOwnedAccount>> by lazy {
        dao.monitorAllEOAs()
    }

    val contacts: LiveData<List<Contact>> by lazy {
        dao.monitorAllContacts()
    }

    /**
     * EOA which gets displayed in {@link AccountsFragment} (if exists)
     */
    var accountToPresent: ExternallyOwnedAccount =
        ExternallyOwnedAccount(-1)

    /**
     * Object represents User Input in {@link AccountCreationFragment} set via two way data binding
     */
    var accountToCreate: ExternallyOwnedAccount =
        ExternallyOwnedAccount()

    /**
     * Object represents User Input in {@link AccountManageFragment} set via two way data binding
     */
    var accountToUpdate: ExternallyOwnedAccount =
        ExternallyOwnedAccount(-1)

    /**
     * Objekt represent User input in {@link ContactManageFragment} set via two way data binding
     */
    val contactToCreate: Contact =
        Contact()

    var contactToUpdate: Contact =
        Contact(-1)

    val dao: AppDao = AppDatabase.getDatabase(getApplication(), viewModelScope).appDao()

    val blockchainService = ServiceLocator.blockchainService()


    val accountBalance: LiveData<String> = liveData(Dispatchers.IO) {
        val accounts = AppDatabase.getDatabase(
            getApplication(), viewModelScope
        ).appDao().loadAllEOAs()
        if (accounts.isNotEmpty()) {
            val balance = blockchainService.getBalance(accounts[0].publicAddress)
            emit(Convert.fromWei(balance.toString(), Convert.Unit.ETHER).round(DECIMAL32).toString().plus(" Ether"))

        } else {
            emit ("Kein Account angegeben")
        }
    }


    suspend fun createAccount() {
        Log.d("AccountsViewModel", accountToCreate.toString())
//        if (!isValidRegularName(accountToCreate.name)
//            || !isValidPublicAddress(accountToCreate.publicAddress)
//            || !isValidPrivateKey(accountToCreate.privateKey)
//        ) {
//            return
//        }

        dao.insertEOA(accountToCreate)
    }

    fun updateAccount() {
        viewModelScope.launch {
            dao.updateEOA(accountToUpdate)
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            dao.deleteEOA(accountToUpdate)
        }
    }

    suspend fun createContact() {
        dao.insertContacts(contactToCreate)
    }

    suspend fun updateContact() {
        if (contactToUpdate.id > -1)
            throw InvalidContactException("Invalid Contact ID")

        dao.updateContacts(contactToUpdate)
    }

    suspend fun deleteContact() = withContext(Dispatchers.IO) {
        dao.deleteContacts(contactToUpdate)
    }
}