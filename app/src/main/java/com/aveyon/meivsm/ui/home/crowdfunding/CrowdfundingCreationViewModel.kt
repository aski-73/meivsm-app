package com.aveyon.meivsm.ui.home.crowdfunding

import android.app.Application
import androidx.lifecycle.*
import com.aveyon.meivsm.db.AppDatabase
import com.aveyon.meivsm.db.Contact
import com.aveyon.meivsm.db.ExternallyOwnedAccount
import com.aveyon.meivsm.services.CrowdfundingTemplateParameters
import com.aveyon.meivsm.services.ServiceLocator
import com.aveyon.meivsm.web3.CrowdfundingContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigInteger

/**
 * View model accessed by Fragments for CrowdfundingContract creation
 */
class CrowdfundingCreationViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * List of accoutns of which a user can choose a receiver
     */
    var accounts: LiveData<List<ExternallyOwnedAccount>> = liveData {
        var accounts = AppDatabase.getDatabase(
            getApplication(), viewModelScope
        ).appDao().loadAllEOAs()
        emit(accounts)
    }

    var contacts: LiveData<List<Contact>> = liveData {
        var contacts = AppDatabase.getDatabase(
            getApplication(), viewModelScope
        ).appDao().loadAllContacts()
        emit(contacts)
    }

    var templateParameters = CrowdfundingTemplateParameters()

    val compilerApiService = ServiceLocator.compilerApiService()

    val blockchainService = ServiceLocator.blockchainService()

    /**
     * Compiles Contract with template parameters and deploys it to blockchain
     */
    suspend fun createNewCrowdfundingContract(eoa: ExternallyOwnedAccount) = withContext(Dispatchers.IO) {
        // Send template params to http server to insert params in tempalte and compile
        val response = compilerApiService.postCrowdfunding(templateParameters).execute()
        val binary = response.body()

        // deploy binary if compiled contract to blockchain
        val contract = blockchainService.deployContract(binary.toString(), eoa)

        // register contract
        blockchainService.registerContract(eoa, contract.contractAddress.toString())

        // make first init transaction in order to make contract interactive via this app
        (contract as CrowdfundingContract).handle("init", BigInteger.ZERO).send()
    }

}