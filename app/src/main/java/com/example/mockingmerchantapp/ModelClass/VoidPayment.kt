package com.example.mockingmerchantapp.ModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VoidPayment(
    @Expose
    @SerializedName("responseCode")
    val responseCode: String,
    @Expose
    @SerializedName("responseMessage")
    val responseMessage: String,
    @Expose
    @SerializedName("data")
    var data: VoidData
)

data class VoidData(
    @Expose
    @SerializedName("ref")
    val ref: String,
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("gw_ref")
    val gw_ref: String
)
