package com.aveyon.meivsm.services

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "http://192.168.0.59:3000"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
        // Set asLenient() to accept Content-Type plain/text
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .baseUrl(BASE_URL)
    .build()

interface CompilerApiService {
    @POST("crowdfunding")
    fun postCrowdfunding(@Body params: CrowdfundingTemplateParameters): Call<String>
}

data class CrowdfundingTemplateParameters(
    var receiver: String = "",
    var title: String = "",
    var goal: String = "",
    var unit: String = "",
    var endDate: Long = 0
)

object CompilerApi {
    val compilerApiService: CompilerApiService by lazy {
        retrofit.create(CompilerApiService::class.java)
    }
}