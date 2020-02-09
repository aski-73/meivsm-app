package com.aveyon.meivsm.services

import org.junit.Test
import java.util.*

class CompilerAPiServiceIT {
    var compilerApiService = CompilerApi.compilerApiService

    @Test
    fun postToCrowdfundingEndpointWithValidParamsReturnsBytecode() {
        // GIVEN
        var endDate: Calendar = Calendar.getInstance().also { c -> c.add(Calendar.YEAR, 1) }

        var params = CrowdfundingTemplateParameters(
            "0x60696E2f6bd0f26386eF6BC23658c43334a9bD76",
            "SomeCrowdfundingProject",
            "10",
            "ether",
            endDate.time.time
        )
        // WHEN
        val bytecode = compilerApiService.postCrowdfunding(params).execute().body()

        // THEN
        println (bytecode)
        assert(bytecode is String)
    }
}