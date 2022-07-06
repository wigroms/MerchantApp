package com.example.mockingmerchantapp.ModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PaymentInquiryRequest(
    @Expose
    @SerializedName("merchant_id")
    val merchant_id: String,
    @Expose
    @SerializedName("terminal_id")
    val terminal_id: String,
    @Expose
    @SerializedName("ref")
    val ref: String,
    @Expose
    @SerializedName("timeout")
    var timeout: Int
)
