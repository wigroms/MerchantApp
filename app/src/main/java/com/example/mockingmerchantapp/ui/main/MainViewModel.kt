package com.example.mockingmerchantapp.ui.main

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.mockingmerchantapp.MainRepository
import com.example.mockingmerchantapp.ModelClass.*
import com.example.mockingmerchantapp.R
import com.example.mockingmerchantapp.databinding.MainFragmentBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    // TODO: Implement the ViewModel
    fun ClickMenu(button: Button, fragmentManager: FragmentManager?) {
        button.setOnClickListener {
            val fragment2 = MenuFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.apply {
                setCustomAnimations(
                    R.anim.fade_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.slide_in,   // popEnter
                    R.anim.slide_out  // popExit
                )
                replace(R.id.container, fragment2)
                commit()
            }
        }
    }

    fun ClickSale(button: ImageView, fragmentManager: FragmentManager?) {
        button.setOnClickListener {
            val fragment2 = MainFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.apply {
                setCustomAnimations(
                    R.anim.fade_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.slide_in,   // popEnter
                    R.anim.slide_out  // popExit
                )
                replace(R.id.container, fragment2)
                commit()
            }
        }

    }

    fun ClickSale(button: Button, fragmentManager: FragmentManager?) {

        button.setOnClickListener {
            val fragment2 = MainFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.apply {
                setCustomAnimations(
                    R.anim.fade_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.slide_in,   // popEnter
                    R.anim.slide_out  // popExit
                )
                replace(R.id.container, fragment2)
                commit()
            }
        }

    }

    fun ClickHistory(button: Button, fragmentManager: FragmentManager?) {
        button.setOnClickListener {
            val fragment2 = HistoryFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.apply {
                setCustomAnimations(
                    R.anim.fade_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.slide_in,   // popEnter
                    R.anim.slide_out  // popExit
                )
                replace(R.id.container, fragment2)
                commit()
            }
        }
    }

    fun ClickHistory(button: ImageView, fragmentManager: FragmentManager?) {
        button.setOnClickListener {
            val fragment2 = HistoryFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.apply {
                replace(R.id.container, fragment2)
                commit()
            }
        }
    }

    fun ClickSetting(button: ImageButton, fragmentManager: FragmentManager?) {
        button.setOnClickListener {
            val fragment2 = SettingFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.apply {
                replace(R.id.container, fragment2)
                commit()
            }
        }

    }

    var transactionInquiry = MutableLiveData<TransactionInquiry>()
    var transactionslist = MutableLiveData<List<Transactions>>()

    fun getTransactionHistory() {

        var tranRequest = TransactionRequest(date = "2022-06-10",payment_method = "kexwallet")
        val response = repository.getTransaction(tranRequest)

        response.enqueue(object : Callback<TransactionInquiry> {
            override fun onResponse(
                call: Call<TransactionInquiry>,
                response: Response<TransactionInquiry>,
            ) {
                transactionInquiry.postValue(response.body())
                transactionslist.postValue(response.body()!!.data.transactions.sortedByDescending { it.when_ })
                transactionslist.value = response.body()!!.data.transactions
                // transactionInquiry.postValue(response.body())
                Log.w("response.body()", "" + response.body().toString())
                Log.w("transactionInquiry", "" + transactionInquiry.value?.data)
                Log.w("transactionList", "" + transactionslist.value)

            }

            override fun onFailure(call: Call<TransactionInquiry>, t: Throwable) {
                //errorMessage.postValue(t.message)
                Log.w("response.body()", "" + t.message)
            }
        })

    }

    fun getTransactionTotalAmount(): Int? {
        return transactionInquiry.value?.data?.transactions?.sumOf { it.amount }
    }

    var res_cscanb = MutableLiveData<CscanB>()
    var res_cscanb_data = MutableLiveData<CSCANB_Data>()

    fun getCsanB(req: CscanBRequest) {

        val response = repository.getQRCscanB(req)
        response.enqueue(object : Callback<CscanB> {
            override fun onResponse(call: Call<CscanB>, response: Response<CscanB>) {

                res_cscanb_data.value = response.body()!!.data
                Log.w("response.body()", "" + response.body().toString())
                Log.w("res_cscanb_data", "" + res_cscanb_data.value)
            }

            override fun onFailure(call: Call<CscanB>, t: Throwable) {
                Log.w("response.body()", "" + t.message)
            }


        })


    }

    var res_payment = MutableLiveData<PaymentInquiry>()
    var res_payment_data = MutableLiveData<Data>()

    fun getStatusPayment(req: PaymentInquiryRequest){

        var response = repository.getStatusPayment(req)
       response.subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           //.repeat(10000)
           .timeout(20,TimeUnit.SECONDS)
           .takeUntil{it.data.status.equals("success")}//processing
           .subscribe(getPaymentInquiryRX())

         /*    var Intervaltime : Observable<Long> =  Observable.interval(5, TimeUnit.SECONDS)
        Intervaltime.subscribe { it ->
            System.out.println("Interval 5 = $it")

        }*/

        /*response.enqueue(object : Callback<PaymentInquiry> {
            override fun onResponse(
                call: Call<PaymentInquiry>,
                response: Response<PaymentInquiry>,
            ) {

                res_payment_data.value = response.body()?.data
                Log.w("response.body()", "" + response.body().toString())
                Log.w("res_payment_data", "" + res_payment_data.value)
            }

            override fun onFailure(call: Call<PaymentInquiry>, t: Throwable) {
                Log.w("response.body()", "" + t.message)
            }


        })*/
    }

    fun getPaymentInquiryRX ():io.reactivex.rxjava3.core.Observer<PaymentInquiry>{
        return object :io.reactivex.rxjava3.core.Observer<PaymentInquiry>{
            override fun onSubscribe(d: Disposable) {
                if(res_payment.value?.data?.status.equals("processing"))
                    d.dispose()
                d
            }

            override fun onNext(t: PaymentInquiry) {
                res_payment.postValue(t)
                res_payment_data.setValue(t.data)
                System.out.println(t.data.status)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

            override fun onComplete() {
                System.out.println("completed.")

            }

        }
    }

    fun getQrCodeBitmap(paymentCode: String): Bitmap {
        val size = 256 //pixels
        val qrCodeContent = "$paymentCode"
        val hints = hashMapOf<EncodeHintType, Int>().also {
            it[EncodeHintType.MARGIN] = 1
        } // Make the QR code buffer border narrower
        val bits = QRCodeWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }


    var Amount_Str = MutableLiveData<String>()

    fun setClickBtn(binding: MainFragmentBinding) {
        binding.button0.setOnClickListener(View.OnClickListener {
            val append = binding.button0
            AppendString(append, binding.textView3)
        })

        binding.button.setOnClickListener(View.OnClickListener {
            val append = binding.button
            AppendString(append, binding.textView3)
        })

        binding.button2.setOnClickListener(View.OnClickListener {
            val append = binding.button2
            AppendString(append, binding.textView3)
        })

        binding.button3.setOnClickListener(View.OnClickListener {
            val append = binding.button3
            AppendString(append, binding.textView3)
        })

        binding.button4.setOnClickListener(View.OnClickListener {
            val append = binding.button4
            AppendString(append, binding.textView3)
        })

        binding.button5.setOnClickListener(View.OnClickListener {
            val append = binding.button5
            AppendString(append, binding.textView3)
        })

        binding.button6.setOnClickListener(View.OnClickListener {
            val append = binding.button6
            AppendString(append, binding.textView3)
        })

        binding.button7.setOnClickListener(View.OnClickListener {
            val append = binding.button7
            AppendString(append, binding.textView3)
        })

        binding.button8.setOnClickListener(View.OnClickListener {
            val append = binding.button8
            AppendString(append, binding.textView3)
        })

        binding.button9.setOnClickListener(View.OnClickListener {
            val append = binding.button9
            AppendString(append, binding.textView3)
        })

        binding.buttonClear.setOnClickListener(View.OnClickListener {
            binding.textView3.text = ""
        })
    }

    fun AppendString(btn: Button, textAmount: TextView) {
        if (!(textAmount.text == "" && btn.text == "0")) {
            var strAppend = btn.text.toString()
            var str = textAmount.text.toString()
            var value = (str + strAppend)
            Amount_Str.value = value
            textAmount.text = Amount_Str.value
        }
    }

    fun GetAmountValueString(): String? {
        return Amount_Str.value
    }

    fun GetAmountValue(): Int? {
        return Integer.valueOf(Amount_Str.value)
    }

    fun ShowDialog(view: View, context: Context) {
        val dialog = Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setOnCancelListener { it.dismiss() }
        dialog.setContentView(R.layout.success_fragment)
        dialog.show()

        // Hide after some seconds
        // Hide after some seconds
        val handler = Handler()
        val runnable = Runnable {
            if (dialog.isShowing()) {
                dialog.dismiss()
            }
        }

        dialog.setOnDismissListener(DialogInterface.OnDismissListener {
            handler.removeCallbacks(runnable)
        })

        handler.postDelayed(runnable, 5000)
    }

}