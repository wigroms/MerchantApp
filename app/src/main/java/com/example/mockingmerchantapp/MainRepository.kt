package com.example.mockingmerchantapp

import android.util.Log
import com.example.mockingmerchantapp.ModelClass.CscanBRequest
import com.example.mockingmerchantapp.ModelClass.PaymentInquiryRequest
import com.example.mockingmerchantapp.ModelClass.Payment_source

import com.example.mockingmerchantapp.ModelClass.TransactionRequest

class MainRepository constructor(private val retrofitService: ApiInterface) {

    var tranRequest = TransactionRequest(date = "2022-06-10",payment_method = "kexwallet")

    fun getTransaction() = retrofitService.TransactionInquiry(tranRequest)

    fun getQRCscanB(CscanBData:CscanBRequest ) = retrofitService.C_SCAN_B(CscanBData)

    fun getStatusPayment(PaymentData:PaymentInquiryRequest ) = retrofitService.PaymentInquiry(PaymentData)

}