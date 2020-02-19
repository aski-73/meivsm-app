package com.aveyon.meivsm.services

import com.aveyon.meivsm.model.entities.ExternallyOwnedAccount
import com.aveyon.meivsm.services.web3.ContractRegistryContract
import com.aveyon.meivsm.services.web3.CrowdfundingContract
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.Assert.assertEquals
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.http.HttpService
import org.web3j.tx.gas.StaticGasProvider
import org.web3j.utils.Convert
import java.math.BigInteger
import java.util.*

/**
 * Integrationtest between BlockchainDiscovery, RegistryContract and Ethereum Node (Ganache)/ Blockchain
 */
class BlockchainServiceIT {

    /**
     * Remote ethereum node
     */
    var ETH_NODE: String = "http://192.168.0.49:7545"

    private val web3 = Web3j.build(HttpService(ETH_NODE))

    lateinit var testSubject: BlockchainService

    lateinit var crowdfundingContract: CrowdfundingContract

    private val registryContractAddr = "0xE43850694D6d64e80c70b96C004E845A9ffCEeE1"

    lateinit var logger: Logger

    private val gasProvider = StaticGasProvider(BigInteger("1"), BigInteger("6721975"))

    /**
     * EOA on test node
     */
    var eoa = ExternallyOwnedAccount(
        0,
        "notrelevant",
        "0x027b61b2340D8Ba16B35e122a969fC9b4484ccc6",
        "2f9029de3aa4d154ab603820a4d2ededddcff6f391aec81f9d2fbdf2cb0afbc1"
    )

    @Before
    fun setUp() {
        testSubject = BlockchainService()
        logger = LoggerFactory.getLogger(javaClass)

        val balance = web3.ethGetBalance(
            Credentials.create(eoa.privateKey).address,
            DefaultBlockParameterName.LATEST
        ).sendAsync().get()
        println(balance.balance.toString())

        // deploy new contract before every test run
        crowdfundingContract = CrowdfundingContract.deploy(
            web3,
            Credentials.create(eoa.privateKey),
            gasProvider
        ).sendAsync().get()

        // register deployed contract in registryContract
        val registryContract = ContractRegistryContract.load(
            registryContractAddr,
            web3,
            Credentials.create(eoa.privateKey),
            gasProvider
        )
        val wei  = BigInteger(Convert.toWei("5", Convert.Unit.ETHER).toString())
        registryContract.register(
            crowdfundingContract.contractAddress,
            BigInteger(Date().time.toString()),
            "crowdfunding",
            wei
        ).sendAsync().get()
    }

    @After
    fun tearDown() {
        // remove deployed contract in registryContract
        val registryContract = ContractRegistryContract.load(
            registryContractAddr,
            web3,
            Credentials.create(eoa.privateKey),
            gasProvider
        )
        val wei = BigInteger(Convert.toWei("5", Convert.Unit.ETHER).toString())
        registryContract.remove(crowdfundingContract.contractAddress, wei).sendAsync().get()
    }

    @Test
    fun getContractCategoryReturnsCrowdfundingCategory() = runBlockingTest {
        // GIVEN
        var contractAddr = crowdfundingContract.contractAddress ?: ""
        // WHEN
        var category = testSubject.getContractCategory(contractAddr, eoa)

        // THEN
        assertEquals("crowdfunding", category)
    }

    @Test
    fun getContractReturnsCrowdfundingContract() = runBlockingTest {
        // GIVEN
        var contractAddr = crowdfundingContract.contractAddress ?: ""

        // WHEN
        var contract =
            testSubject.getContract(contractAddr, "crowdfunding", eoa)

        // THEN
        assert(contract is CrowdfundingContract)
    }
}
