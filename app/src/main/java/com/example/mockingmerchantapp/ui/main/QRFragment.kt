package com.example.mockingmerchantapp.ui.main

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mockingmerchantapp.*
import com.example.mockingmerchantapp.ModelClass.CscanBRequest
import com.example.mockingmerchantapp.ModelClass.Invoice
import com.example.mockingmerchantapp.ModelClass.PaymentInquiryRequest
import com.example.mockingmerchantapp.ModelClass.Payment_source
import com.example.mockingmerchantapp.databinding.PaymentMedthodFragmentBinding
import com.example.mockingmerchantapp.databinding.QrFragmentBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class QRFragment: Fragment()  {

    private var App: Int = 0
    private var Amount = 0
    private val binding: QrFragmentBinding by lazy { QrFragmentBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = QRFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            Amount = bundle.getInt("Amount", android.R.attr.defaultValue)
            App = bundle.getInt("App", android.R.attr.defaultValue)
            Log.d("Amount",""+Amount)
        }
       // viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val df = SimpleDateFormat("HHmmssZ", Locale.US)
        val cals: Calendar = Calendar.getInstance()
        val currentDate: String = df.format(cals.getTime())

        var paymentsoruce = Payment_source("kexwallet")
        var invoice = Invoice("21 TST Tower","คนไทย ใจดี")
        var requester = CscanBRequest("511516"+currentDate,"M299",//5516627
            "1","1",Amount,"Coke test 20 Baht",
            paymentsoruce,"Baht","12316515665","1","BSS_vending",invoice)

        if(App == 1) {
            binding.imageLogo.background = resources.getDrawable(R.drawable.kerry_logo)
        }else if(App == 2){
            binding.imageLogo.background = resources.getDrawable(R.drawable.rr_logo)
        }

        viewModel =
            ViewModelProvider(this, ViewModelFactory(MainRepository(ApiInterface.ApiClient.apiInterface))).get(
                MainViewModel::class.java)

        viewModel.getCsanB(requester)

        viewModel.res_cscanb_data.observe(viewLifecycleOwner, Observer {
            Log.d("observe cscanb", "onCreate: $it")
            binding.imageView2.setVisibility(View.VISIBLE);
            try {
                binding.imageView2.setImageBitmap(viewModel.getQrCodeBitmap(it.payment_code))

                val timer = object: CountDownTimer(5000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.textView2.text = (millisUntilFinished/1000L).toString()
                        Log.d("Timer", " "+millisUntilFinished)
                        }
                    override fun onFinish() {

                        val dialog = Dialog(context!!, android.R.style.Theme_Black_NoTitleBar)
                        dialog.setCanceledOnTouchOutside(true)
                        dialog.setOnCancelListener { it.dismiss() }
                        dialog.setContentView(R.layout.fail_fragment)
                        dialog.show()
                        // Hide after some seconds
                        // Hide after some seconds
                        val fragment2 = MainFragment()
                        val fragmentManager: FragmentManager? = fragmentManager
                        val fragmentTransaction: FragmentTransaction =
                            fragmentManager!!.beginTransaction()
                        var fm = fragmentManager.findFragmentById(id)
                        fragmentTransaction.apply {
                            setCustomAnimations(
                                R.anim.slide_in,  // enter
                                R.anim.slide_out,  // exit
                                R.anim.fade_in,   // popEnter
                                R.anim.fade_out  // popExit
                            )
                            //fragmentManager.popBackStack()
                            if (fm != null) {
                                remove(fm)
                            }
                            replace(R.id.container, fragment2)
                            commit()
                        }

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
                        Log.d("Timer", "Time Out")
                    }
                }
                timer.start()

                var Paymentrequester = PaymentInquiryRequest("M299","1",it.ref,5)
                Log.d("observe Payment", "onCreate: $Paymentrequester")

                viewModel.getStatusPayment(Paymentrequester)

            }catch (ex : Exception){
                Toast.makeText(context, "Generate QR CODE Failed.", Toast.LENGTH_SHORT).show()
            }

        })

        viewModel.res_payment_data.observe(viewLifecycleOwner, Observer {
            Log.d("observe payment", "onCreate: $it")
            try {
                it.status

            }catch (ex : Exception){
                Toast.makeText(context, "RequestFailed", Toast.LENGTH_SHORT).show()
            }

        })


        Log.d("GetAmountValueString", ""+Amount.toString())
            binding.textView.text = Amount.toString()


        // TODO: Use the ViewModel
        binding.button10.setOnClickListener {
            val fragment2 = Payment_Method_Fragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
          var fm = fragmentManager.findFragmentById(id)
            fragmentTransaction.apply {
                setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.slide_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.fade_out  // popExit
                )
                //fragmentManager.popBackStack()
                if (fm != null) {
                    remove(fm)
                }
                commit()
            }
        }

    }

}