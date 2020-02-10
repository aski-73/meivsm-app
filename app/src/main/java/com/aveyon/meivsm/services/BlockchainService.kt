package com.aveyon.meivsm.services

import com.aveyon.meivsm.db.ExternallyOwnedAccount
import com.aveyon.meivsm.web3.GenericContractInterface
import com.aveyon.meivsm.web3.ContractRegistryContract
import com.aveyon.meivsm.web3.CrowdfundingContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.*
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger
import java.util.*


/**
 * Manages Connection and rpc calls to remote ethereum node
 */
class BlockchainService {
    /**
     * Remote ethereum node
     */
    var ETH_NODE: String = "http://192.168.0.59:7545"

    private val web3 = Web3j.build(HttpService(ETH_NODE))

    /**
     * Address of the ContractRegistryContract in the test net (Ganache)
     */
    private val registryContractAddr = "0x6B133f68D06D57dC9C7880678bd5da704C912af6"


    private val gasProvider = StaticGasProvider(BigInteger("1"), BigInteger("6721975"))

    /**
     * Deploy a contract in binary format (compiled solidity code => bytecode) to blockchain
     * with web3j
     */
    suspend fun deployContract(binary: String, eoa: ExternallyOwnedAccount): GenericContractInterface =
        withContext(Dispatchers.IO) {

            // Use web3j to deploy new Contract
            val contract = CrowdfundingContract.deployRemoteCall(
                CrowdfundingContract::class.java,
                web3,
                Credentials.create(eoa.privateKey),
                gasProvider,
                binary,
                ""
            ).send()

            contract
        }

    /**
     * Register a deployed contract to the ContractRegistryContract, so the app can load
     * contracts to interact with
     */
    suspend fun registerContract(eoa: ExternallyOwnedAccount, addr: String) = withContext(Dispatchers.IO) {
        val registryContract = ContractRegistryContract.load(
            registryContractAddr,
            web3,
            Credentials.create(eoa.privateKey),
            gasProvider
        )
        val wei  = BigInteger(Convert.toWei("5", Convert.Unit.ETHER).toString())
        registryContract.register(
            addr,
            BigInteger(Date().time.toString()),
            "crowdfunding",
            wei
        ).send()
    }

    suspend fun getLatestBlock(): EthBlock {
        var ethblock =
            web3.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).sendAsync().get()
        return ethblock
    }

    suspend fun getAccounts(): EthAccounts {
        return web3.ethAccounts().sendAsync().get()
    }

    /**
     * Reads all Created Contracts on the blockchain
     */
    suspend fun getAllContractsAddresses(eoa: ExternallyOwnedAccount): List<String> {
        web3.ethGetCompilers()
        var contract = ContractRegistryContract.load(
            registryContractAddr,
            web3,
            Credentials.create(eoa.privateKey),
            gasProvider
        )
        return contract.getAllContracts().sendAsync().get() as List<String>
    }

    suspend fun getCompilers(): List<String> {
        return web3.ethGetCompilers().sendAsync().get().compilers
    }

    suspend fun getContractBinary(addr: String): EthGetCode {
        return web3.ethGetCode(addr, DefaultBlockParameter.valueOf("latest")).sendAsync().get()
    }

    suspend fun getContractCategory(addr: String, eoa: ExternallyOwnedAccount): String {
        var contract: ContractRegistryContract = ContractRegistryContract.load(
            registryContractAddr,
            web3,
            Credentials.create(eoa.privateKey),
            gasProvider
        )
        var i = contract.registryCheck(addr).sendAsync().get().component1()
        return contract.registry(i).sendAsync().get().component3()
    }

    /**
     * Gets dynamically a contract from an Ethereum Node given its public address. Since web3 is
     * used as an interface, the bytecode must be supplied to deliver a different contract of the
     * same category
     */
    suspend fun getContract(
        addr: String,
        category: String,
        eoa: ExternallyOwnedAccount
    ): GenericContractInterface {
        var contract: GenericContractInterface

        // Since only crowdfunding contracts exists..
        contract =
            CrowdfundingContract.load(addr, web3, Credentials.create(eoa.privateKey), gasProvider)

        // TODO implement more contract categories

        return contract

    }

    /**
     * Gets all contracts listed in RegistryContract as objects of type {@see GenericContractInterface}
     */
    suspend fun getAllContracts(eoa: ExternallyOwnedAccount): List<GenericContractInterface> =
        withContext(Dispatchers.IO) {
            var contractAddresses = getAllContractsAddresses(eoa)
            var contracts = LinkedList<GenericContractInterface>()

            contractAddresses.forEach { addr ->
                var category = getContractCategory(addr, eoa)
                contracts.add(getContract(addr, category, eoa))
            }

            contracts
        }
}