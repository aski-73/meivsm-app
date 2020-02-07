package com.aveyon.meivsm.services

/**
 * Simple manual DI Provider via Kotlin Singleton
 */
object ServiceLocator {
    fun blockchainDiscovery(): BlockchainDiscovery {
        return BlockchainDiscovery()
    }
}