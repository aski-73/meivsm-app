package com.aveyon.meivsm.services

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.math.BigInteger

private const val BASE_URL = "http://192.168.0.59:3000"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface CompilerApiService {
    @POST("crowdfunding")
    fun postCrowdfunding(@Body params: CrowdfundingTemplateParameters): Call<String>
}

data class CrowdfundingTemplateParameters(
    var receiver: String,
    var title: String,
    var goal: String,
    var unit: String,
    var endDate: Int
)

object CompilerApi {
    val compilerApiService: CompilerApiService by lazy {
        retrofit.create(CompilerApiService::class.java)
    }
}