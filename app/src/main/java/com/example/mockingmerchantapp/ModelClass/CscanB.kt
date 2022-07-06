package com.example.mockingmerchantapp.ModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CscanB(
    @Expose
    @SerializedName("responseCode")
    val responseCode: String,
    @Expose
    @SerializedName("responseMessage")
    val responseMessage: String,
    @Expose
    @SerializedName("data")
    var data: CSCANB_Data
)

data class CSCANB_Data(
    @Expose
    @SerializedName("ref")
    val ref: String,
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("payment_method")
    val payment_method: String,
    @Expose
    @SerializedName("payment_code")
    val payment_code: String,
    @Expose
    @SerializedName("type")
    val type: String,
    @Expose
    @SerializedName("key")
    val key: String,
    @Expose
    @SerializedName("gw")
    val gw: String
)
