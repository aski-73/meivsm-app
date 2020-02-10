package com.aveyon.meivsm.ui.contracts.crowdfunding

import android.util.Log
import androidx.lifecycle.*
import com.aveyon.meivsm.services.ServiceLocator
import com.aveyon.meivsm.ui.Validator
import com.aveyon.meivsm.web3.CrowdfundingContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.utils.Convert
import java.math.BigInteger

class CrowdfundingViewModel(var contract: CrowdfundingContract) : ViewModel() {

    /**
     * get goal with liveData builder function. Suspend function that suspends ands resume on main thread,
     * bit runs on IO Thread. Creates a LiveData object. When
     */
    val goal: LiveData<BigInteger> = liveData(Dispatchers.IO) {
        emit(contract.goal().send())
    }

    /**
     * get sum with liveData builder function
     */
    val sum: LiveData<BigInteger> = liveData(Dispatchers.IO) {
        emit(contract.sum().send())
    }

    /**
     * get endDate with liveData builder function
     */
    val endDate: LiveData<BigInteger> = liveData(Dispatchers.IO) {
        emit(contract.endDate().send())
    }

    val state: LiveData<String?> = liveData(Dispatchers.IO) {
        emit(contract.state()?.send())
    }

    /**
     * Bind values of {@link CrowdfundingPayingFragment} in order to withstand spontaneous
     * user navigation
     */
    var payValue: String = ""
    var payValueUnit: String = ""


    /**
     * Make a pay* transaction to the contract
     */
    suspend fun makePayTransaction(): TransactionReceipt = withContext(Dispatchers.IO) {
        var weiValue: BigInteger
        if (payValueUnit.toLowerCase() == "ether") {
            weiValue = Convert.toWei(payValue, Convert.Unit.ETHER).toBigInteger()
        } else { // else 'wei'. no other units supported at the moment
            weiValue = BigInteger(payValue)
        }
        Log.d(javaClass.name, weiValue.toString())
        contract.handle("pay*", weiValue).send()
    }
}

class CrowdfundingViewModelFactory(var contract: CrowdfundingContract) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CrowdfundingViewModel(contract) as T
    }
}