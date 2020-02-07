package com.aveyon.meivsm.interfaces

import org.web3j.protocol.core.RemoteFunctionCall

/**
 * Requirements for a contract
 */
interface GenericContractInterface {

    var contractAddress: String?
    var category: String
    fun title(): RemoteFunctionCall<String?>?
    fun state(): RemoteFunctionCall<String?>?

}