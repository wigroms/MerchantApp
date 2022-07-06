package com.example.mockingmerchantapp.ModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentInquiry(
    @Expose
    @SerializedName("responseCode")
    val responseCode: String,
    @Expose
    @SerializedName("responseMessage")
    val responseMessage: String,
    @Expose
    @SerializedName("data")
    var data: Data
)

data class Data(
    @Expose
    @SerializedName("status")
    var status: String
)
