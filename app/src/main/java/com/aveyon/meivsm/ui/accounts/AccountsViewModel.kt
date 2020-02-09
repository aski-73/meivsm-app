package com.aveyon.meivsm.ui.accounts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aveyon.meivsm.db.*
import com.aveyon.meivsm.ui.Validator
import com.aveyon.meivsm.ui.Validator.Companion.isValidPrivateKey
import com.aveyon.meivsm.ui.Validator.Companion.isValidPublicAddress
import com.aveyon.meivsm.ui.Validator.Companion.isValidRegularName
import com.aveyon.meivsm.ui.isPrivateKey
import com.aveyon.meivsm.ui.isPublicAddress
import kotlinx.coroutines.launch

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
    var accountToPresent: ExternallyOwnedAccount = ExternallyOwnedAccount(-1)

    /**
     * Object represents User Input in {@link AccountCreationFragment} set via two way data binding
     */
    var accountToCreate: ExternallyOwnedAccount = ExternallyOwnedAccount()

    /**
     * Object represents User Input in {@link AccountManageFragment} set via two way data binding
     */
    var accountToUpdate: ExternallyOwnedAccount = ExternallyOwnedAccount(-1)

    /**
     * Objekt represent User input in {@link ContactManageFragment} set via two way data binding
     */
    val contactToCreate: Contact = Contact(-1)

    var contactToUpdate: Contact = Contact(-1)

    val dao: AppDao = AppDatabase.getDatabase(getApplication(), viewModelScope).appDao()


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

    fun createContact() {
        viewModelScope.launch {
            dao.insertContacts(contactToCreate)
        }
    }

    fun updateContact() {
        if (contactToUpdate.id > -1)
            return

        viewModelScope.launch {
            dao.updateContacts(contactToUpdate)
        }
    }
}