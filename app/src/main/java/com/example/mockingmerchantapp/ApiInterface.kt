package com.example.mockingmerchantapp

import android.util.Log
import com.example.mockingmerchantapp.ModelClass.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @POST("merchant/v1/createpaymentCSB")
    fun C_SCAN_B(@Body CscanBrequest:CscanBRequest
    ): Call<CscanB>

    @POST("merchant/v1/transactions")
    fun TransactionInquiry(@Body body:TransactionRequest): Call<TransactionInquiry>

    @POST("merchant/v1/waitforpayment")
    fun PaymentInquiry(@Body body:PaymentInquiryRequest): Call<PaymentInquiry>


    object ApiClient {

        val apiInterface: ApiInterface
        private val retrofit: Retrofit
        private val DEFAULT_TIMEOUT = 5L
        private val okHttpClient: OkHttpClient

        init {
            val longging = Interceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")
                        .addHeader("app-id", "BSSTEST")
                        .addHeader("authentication", "3dp7Ytvraddoud3zQlHKrFh4apdOd2chVE8n4llg6NU=")
                        .addHeader("nonce", "today")
                        .build()

                 Log.w("okhttp", "okhttp--->" + request.url().toString())
                chain.proceed(request)
            }


            okHttpClient = OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(longging)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl("https://91151b51c56a4caabcea204b8837fe59.apig.ap-southeast-2.huaweicloudapis.com/caribbean/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            Log.w("retrofit",""+retrofit.baseUrl().uri())
            apiInterface = retrofit.create(ApiInterface::class.java)

        }
    }
}