package com.aveyon.meivsm.ui.contracts.crowdfunding

import androidx.lifecycle.ViewModel
import com.aveyon.meivsm.web3.CrowdfundingContract
import java.math.BigInteger

class CrowdfundingViewModel : ViewModel() {

    var contract: CrowdfundingContract

    lateinit var payValue: BigInteger

    lateinit var payValueUnit: String

    suspend fun doPayTransaction() {

    }
}