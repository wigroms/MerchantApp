package com.example.mockingmerchantapp.ModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TransactionRequest(
    @Expose
    @SerializedName("date")
    val date: String,
    @Expose
    @SerializedName("merchant_id")
    val merchant_id: String,
    @Expose
    @SerializedName("status")
    val status: String
)
