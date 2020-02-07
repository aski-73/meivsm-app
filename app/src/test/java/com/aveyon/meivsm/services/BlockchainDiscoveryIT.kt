package com.aveyon.meivsm.services

import com.aveyon.meivsm.db.ExternallyOwnedAccount
import com.aveyon.meivsm.web3.ContractRegistryContract
import com.aveyon.meivsm.web3.CrowdfundingContract
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
class BlockchainDiscoveryIT {

    /**
     * Remote ethereum node
     */
    var ETH_NODE: String = "http://192.168.0.59:7545"

    private val web3 = Web3j.build(HttpService(ETH_NODE))

    lateinit var testSubject: BlockchainDiscovery

    lateinit var crowdfundingContract: CrowdfundingContract

    private val registryContractAddr = "0xD1756Acb47e6799E90d1773C7864416dF427A344"

    lateinit var logger: Logger

    private val gasProvider = StaticGasProvider(BigInteger("1"), BigInteger("6721975"))

    /**
     * Address to a deployed crowdfunding contract (TODO: deploy contract before every test run
     * instead of having a predefined value)
     */
    val crowdAddr = "0xD635bCD5475C970A272f3aD54640EA2Fc9C63bD4"

    /**
     * EOA on test node
     */
    var eoa = ExternallyOwnedAccount(
        0,
        "notrelevant",
        "0x7F181DeF2E46196a239aC423a2b77e2E6A4d54a6",
        "68e7c21353103efe69c8b521fbd39fb810476b76d9e28caa0a0cca1d9a467493"
    )

    @Before
    fun setUp() {
        testSubject = BlockchainDiscovery()
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
        var wei: BigInteger = BigInteger(Convert.toWei("5", Convert.Unit.ETHER).toString())
        registryContract.register(
            crowdfundingContract.contractAddress,
            BigInteger(Date().time.toString()),
            "crowdfunding",
            wei
        ).sendAsync().get()
    }

    @After
    fun tearDown() {
        // register deployed contract in registryContract
        val registryContract = ContractRegistryContract.load(
            registryContractAddr,
            web3,
            Credentials.create(eoa.privateKey),
            gasProvider
        )
        var wei: BigInteger = BigInteger(Convert.toWei("5", Convert.Unit.ETHER).toString())
        registryContract.remove(crowdfundingContract.contractAddress, wei).sendAsync().get()
    }

//    @Test
//    fun getAllContractsReturnsListOfStrings() = runBlockingTest {
//        // GIVEN
//        var eoa = ExternallyOwnedAccount(
//            0,
//            "notrelevant",
//            "",
//            "0x7F181DeF2E46196a239aC423a2b77e2E6A4d54a6"
//        )
//
//
//        // WHEN
//        var contracts = testSubject.getAllContracts(eoa)
//        contracts.forEach { c -> println(c) }
//
//        // THEN
//        assert(contracts is List<String>)
//    }

//    @Test
//    fun getCompilersReturnsSolidityCompiler() = runBlockingTest {
//        testSubject.getCompilers().forEach({ s -> println(s) })
//    }

//    @Test
//    fun getContractBinary() = runBlockingTest {
//        // GIVEN
//        val addr = "0xD635bCD5475C970A272f3aD54640EA2Fc9C63bD4"
//
//        // WHEN
//        val ethCode = testSubject.getContractBinary(addr)
//
//        // THEN
//        println(ethCode.code)
//
//    }

    @Test
    fun getContractCategoryReturnsCrowdfundingCategory() = runBlockingTest {
        // GIVEN

        // WHEN
        var category = testSubject.getContractCategory(crowdfundingContract.contractAddress, eoa)

        // THEN
        assertEquals(category, "crowdfunding")
    }

    @Test
    fun getContractReturnsCrowdfundingContract() = runBlockingTest {
        // GIVEN

        // WHEN
        var contract =
            testSubject.getContract(crowdfundingContract.contractAddress, "crowdfunding", eoa)

        // THEN
        assert(contract is CrowdfundingContract)
    }
}
