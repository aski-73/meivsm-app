package com.aveyon.meivsm.services

/**
 * Simple manual DI Provider via Kotlin Singleton
 */
object ServiceLocator {

    val blockchainService: BlockchainService by lazy {
        BlockchainService()
    }

    fun blockchainService(): BlockchainService {
        return blockchainService
    }

    fun compilerApiService(): CompilerApiService {
        return CompilerApi.compilerApiService
    }
}