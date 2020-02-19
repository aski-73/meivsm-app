package com.aveyon.meivsm.ui.contracts

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.aveyon.meivsm.services.db.AppDatabase
import com.aveyon.meivsm.services.web3.GenericContractInterface
import com.aveyon.meivsm.services.ServiceLocator
import com.aveyon.meivsm.utils.exceptions.NoAccountExistsException
import com.aveyon.meivsm.services.web3.CrowdfundingContract
import com.aveyon.meivsm.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContractsViewModel(
    application: Application
) :
    AndroidViewModel(application) {

    var contracts: LiveData<List<GenericContractInterface>> = liveData {
        var accounts = AppDatabase.getDatabase(
            getApplication(), viewModelScope
        ).appDao().loadAllEOAs()

        if (accounts.isNotEmpty()) {
            // for simplicity provide the first EOA for blockchain discovery service
            emit(blockchainDiscovery.getAllContracts(accounts[0]))
        }
    }

    private val blockchainDiscovery = ServiceLocator.blockchainService()

    lateinit var selectedContract: GenericContractInterface

    /**
     * Determine next {@see NavDirections} by contract state
     *
     * @return Observable emitting NavDirections
     */
    suspend fun getCrowdfundingAction(contract: GenericContractInterface): NavDirections =
        withContext(Dispatchers.IO) {

            var accounts = AppDatabase.getDatabase(
                getApplication(), viewModelScope
            ).appDao().loadAllEOAs()

            if (accounts.isEmpty())
                throw NoAccountExistsException(getApplication<Application>().getString(R.string.error_noaccounts))

            var navDirection: NavDirections
            Log.d(javaClass.name, (contract as CrowdfundingContract).state()?.send().toString())
            Log.d(javaClass.name, (contract as CrowdfundingContract).sum().send().toString())
            when ((contract as CrowdfundingContract).state()?.send()) {
                "CREATED" -> {
                    navDirection =
                        ContractsFragmentDirections.actionContractsFragmentToCrowdfundingFundingState()

                }
                "FUNDING" -> {
                    navDirection =
                        ContractsFragmentDirections.actionContractsFragmentToCrowdfundingFundingState()

                }
                "SUCCESSFUL" -> {
                    navDirection =
                        ContractsFragmentDirections.actionContractsFragmentToCrowdfundingEndState()
                }
                "FAILED" -> {
                    navDirection =
                        ContractsFragmentDirections.actionContractsFragmentToCrowdfundingEndState()
                }
                else -> {
                    navDirection = ActionOnlyNavDirections(R.id.action_global_contractsFragment)
                }
            }

            navDirection
        }
}