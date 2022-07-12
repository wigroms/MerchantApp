package com.example.mockingmerchantapp

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.mockingmerchantapp.ModelClass.*

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class MainRepository constructor(private val retrofitService: ApiInterface) {

    fun getTransaction(tranRequest:TransactionRequest ) = retrofitService.TransactionInquiry(tranRequest)

    fun getQRCscanB(CscanBData:CscanBRequest ) = retrofitService.C_SCAN_B(CscanBData)

    fun getStatusPayment(PaymentData:PaymentInquiryRequest ) = retrofitService.PaymentInquiry(PaymentData)

    fun getVoidPayment(VoidData:VoidPaymentRequest ) = retrofitService.VoidPayment(VoidData)

}