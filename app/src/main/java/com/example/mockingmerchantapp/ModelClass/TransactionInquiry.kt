package com.example.mockingmerchantapp.ModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TransactionInquiry(
    @Expose
    @SerializedName("responseCode")
    val responseCode: String,
    @Expose
    @SerializedName("responseMessage")
    val responseMessage: String,
    @Expose
    @SerializedName("data")
    var data: DataS
)

data class DataS(
    @Expose
    @SerializedName("count")
    var count: Int,
    @Expose
    @SerializedName("transactions")
    var transactions: List<Transactions>
)

data class Transactions(
    @Expose
    @SerializedName("ref")
    val ref: String,
    @Expose
    @SerializedName("when")
    val when_: String,
    @Expose
    @SerializedName("amount")
    var amount: Double,
    @Expose
    @SerializedName("status")
    val status: String,
    @Expose
    @SerializedName("gw_ref")
    val gw_ref: String,
    @Expose
    @SerializedName("gw_when")
    val gw_when: String,
    @Expose
    @SerializedName("merchant_id")
    val merchant_id: String,
    @Expose
    @SerializedName("sof")
    val sof: String,
    @Expose
    @SerializedName("terminal_id")
    val terminal_id: String,
    @Expose
    @SerializedName("source_origin")
    val source_origin: String,
    @Expose
    @SerializedName("source_txn_ref")
    val source_txn_ref: String,
    @Expose
    @SerializedName("gw_account")
    val gw_account: String,
    @Expose
    @SerializedName("invoice")
    val invoice: String
)
