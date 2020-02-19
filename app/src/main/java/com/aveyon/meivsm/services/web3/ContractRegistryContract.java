package com.aveyon.meivsm.services.web3;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.14.
 */
@SuppressWarnings("rawtypes")
public class ContractRegistryContract extends Contract {
    public static final String BINARY = "0x608060405234801561001057600080fd5b50610da3806100206000396000f3fe6080604052600436106100555760003560e01c806318d3ce961461005a57806329092d0e146100c65780635893253c1461010a578063b7aaf14d146101f8578063c879657214610268578063f11b1b881461027f575b600080fd5b34801561006657600080fd5b5061006f610364565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156100b2578082015181840152602081019050610097565b505050509050019250505060405180910390f35b610108600480360360208110156100dc57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061044b565b005b34801561011657600080fd5b506101436004803603602081101561012d57600080fd5b81019080803590602001909291905050506107ab565b604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156101bb5780820151818401526020810190506101a0565b50505050905090810190601f1680156101e85780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561020457600080fd5b506102476004803603602081101561021b57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061089a565b60405180838152602001821515151581526020019250505060405180910390f35b34801561027457600080fd5b5061027d6108cb565b005b6103626004803603606081101561029557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803590602001906401000000008111156102dc57600080fd5b8201836020820111156102ee57600080fd5b8035906020019184600183028401116401000000008311171561031057600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610945565b005b60608060008054905060405190808252806020026020018201604052801561039b5781602001602082028038833980820191505090505b50905060008090505b60008054905081101561044357600081815481106103be57fe5b906000526020600020906003020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168282815181106103fc57fe5b602002602001019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff168152505080806001019150506103a4565b508091505090565b674563918244f400003410156104c9576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f666565206e6f7420656e6f75676800000000000000000000000000000000000081525060200191505060405180910390fd5b6000808054905014156104db576107a8565b600160008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900460ff16610534576107a8565b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160006101000a81548160ff02191690831515021790555060016000805490501115610744576000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000015490506000600160008054905003815481106105f957fe5b90600052602060002090600302016000828154811061061457fe5b90600052602060002090600302016000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060018201548160010155600282018160020190805460018160011615610100020316600290046106ba929190610c03565b5090505080600160008084815481106106cf57fe5b906000526020600020906003020160000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000181905550505b600080548061074f57fe5b6001900381819060005260206000209060030201600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff021916905560018201600090556002820160006107a39190610c8a565b505090555b50565b600081815481106107b857fe5b90600052602060002090600302016000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690806001015490806002018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156108905780601f1061086557610100808354040283529160200191610890565b820191906000526020600020905b81548152906001019060200180831161087357829003601f168201915b5050505050905083565b60016020528060005260406000206000915090508060000154908060010160009054906101000a900460ff16905082565b600073e98a2ca17f4a56b07e458ed85e00ebbfde6f415c90508073ffffffffffffffffffffffffffffffffffffffff166108fc3073ffffffffffffffffffffffffffffffffffffffff16319081150290604051600060405180830381858888f19350505050158015610941573d6000803e3d6000fd5b5050565b670de0b6b3a76400003410156109c3576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600e8152602001807f666565206e6f7420656e6f75676800000000000000000000000000000000000081525060200191505060405180910390fd5b600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900460ff1615610a86576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f636f6e747261637420616c72656164792072656769737472617465640000000081525060200191505060405180910390fd5b600060405180606001604052808573ffffffffffffffffffffffffffffffffffffffff168152602001848152602001838152509080600181540180825580915050906001820390600052602060002090600302016000909192909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550602082015181600101556040820151816002019080519060200190610b51929190610cd2565b5050505060018060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160006101000a81548160ff021916908315150217905550600160008054905003600160008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000181905550505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610c3c5780548555610c79565b82800160010185558215610c7957600052602060002091601f016020900482015b82811115610c78578254825591600101919060010190610c5d565b5b509050610c869190610d52565b5090565b50805460018160011615610100020316600290046000825580601f10610cb05750610ccf565b601f016020900490600052602060002090810190610cce9190610d52565b5b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610d1357805160ff1916838001178555610d41565b82800160010185558215610d41579182015b82811115610d40578251825591602001919060010190610d25565b5b509050610d4e9190610d52565b5090565b610d7491905b80821115610d70576000816000905550600101610d58565b5090565b9056fea165627a7a72305820ae061362f9d55947fabfa988166f628ec9915fe4431c7aeaa5a4baa68685a3e10029";

    public static final String FUNC_REGISTRY = "registry";

    public static final String FUNC_REGISTRYCHECK = "registryCheck";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_COLLECTFEES = "collectFees";

    public static final String FUNC_GETALLCONTRACTS = "getAllContracts";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("5777", "0x0943070562b0B26a6B02475933f9901a19043B22");
    }

    @Deprecated
    protected ContractRegistryContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ContractRegistryContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ContractRegistryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ContractRegistryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Tuple3<String, BigInteger, String>> registry(BigInteger param0) {
        final Function function = new Function(FUNC_REGISTRY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple3<String, BigInteger, String>>(function,
                new Callable<Tuple3<String, BigInteger, String>>() {
                    @Override
                    public Tuple3<String, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, BigInteger, String>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<Tuple2<BigInteger, Boolean>> registryCheck(String param0) {
        final Function function = new Function(FUNC_REGISTRYCHECK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
        return new RemoteFunctionCall<Tuple2<BigInteger, Boolean>>(function,
                new Callable<Tuple2<BigInteger, Boolean>>() {
                    @Override
                    public Tuple2<BigInteger, Boolean> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, Boolean>(
                                (BigInteger) results.get(0).getValue(), 
                                (Boolean) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> register(String contractAddress, BigInteger timestamp, String category, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(contractAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(timestamp), 
                new org.web3j.abi.datatypes.Utf8String(category)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> remove(String contractAddress, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REMOVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(contractAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> collectFees() {
        final Function function = new Function(
                FUNC_COLLECTFEES, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<List> getAllContracts() {
        final Function function = new Function(FUNC_GETALLCONTRACTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    @Deprecated
    public static ContractRegistryContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractRegistryContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ContractRegistryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ContractRegistryContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ContractRegistryContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ContractRegistryContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ContractRegistryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ContractRegistryContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ContractRegistryContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ContractRegistryContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ContractRegistryContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractRegistryContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ContractRegistryContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ContractRegistryContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ContractRegistryContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ContractRegistryContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
