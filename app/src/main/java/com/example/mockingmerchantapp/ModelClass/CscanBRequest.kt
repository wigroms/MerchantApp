package com.example.mockingmerchantapp.ModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CscanBRequest(
    @Expose
    @SerializedName("request_id")
    val request_id: String,
    @Expose
    @SerializedName("merchant_id")
    val merchant_id: String,
    @Expose
    @SerializedName("branch_id")
    val branch_id: String,
    @Expose
    @SerializedName("terminal_id")
    val terminal_id: String,
    @Expose
    @SerializedName("amount")
    var amount: Int,
    @Expose
    @SerializedName("description")
    val description: String,
    @Expose
    @SerializedName("payment_source")
    var payment_source: Payment_source,
    @Expose
    @SerializedName("currency")
    val currency: String,
    @Expose
    @SerializedName("transaction_id")
    val transaction_id: String,
    @Expose
    @SerializedName("shop_id")
    val shop_id: String,
    @Expose
    @SerializedName("shop_name")
    val shop_name: String,
    @Expose
    @SerializedName("invoice")
    var invoice: Invoice
)

data class Invoice(
    @Expose
    @SerializedName("Address")
    val Address: String,
    @Expose
    @SerializedName("Seller")
    val Seller: String
)

data class Payment_source(
    @Expose
    @SerializedName("payment_method")
    val payment_method: String
)
