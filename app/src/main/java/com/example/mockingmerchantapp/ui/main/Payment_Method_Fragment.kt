package com.example.mockingmerchantapp.ui.main

import android.R.attr.defaultValue
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.mockingmerchantapp.ApiInterface
import com.example.mockingmerchantapp.MainRepository
import com.example.mockingmerchantapp.R
import com.example.mockingmerchantapp.ViewModelFactory
import com.example.mockingmerchantapp.databinding.PaymentMedthodFragmentBinding


class Payment_Method_Fragment : Fragment()  {


    private var Amount: Int = 0

    private val binding: PaymentMedthodFragmentBinding by lazy { PaymentMedthodFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = Payment_Method_Fragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
             Amount = bundle.getInt("Amount", defaultValue)
            Log.d("Amount",""+Amount)
        }

        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(MainRepository(ApiInterface.ApiClient.apiInterface))).get(
                MainViewModel::class.java)
        // TODO: Use the ViewModel

        val fragmentManager: FragmentManager? = fragmentManager
        viewModel.ClickMenu(binding.buttonMenu,fragmentManager)
        viewModel.ClickHistory(binding.buttonHistory,fragmentManager)
        binding.imageView7.setOnClickListener {
            val fragment2 = QRFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.apply {
                setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
                )
                val bundle = Bundle()
                bundle.putInt("Amount", Amount)
                bundle.putInt("App", 1)
                fragment2.setArguments(bundle)
                add(R.id.container, fragment2)
                commit()
            }
        }

        binding.imageView5.setOnClickListener {
            val fragment2 = QRFragment()
            val fragmentManager: FragmentManager? = fragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager!!.beginTransaction()
            fragmentTransaction.apply {
                setCustomAnimations(
                    R.anim.slide_in,  // enter
                    R.anim.fade_out,  // exit
                    R.anim.fade_in,   // popEnter
                    R.anim.slide_out  // popExit
                )
                val bundle = Bundle()
                bundle.putInt("Amount", Amount)
                bundle.putInt("App", 2)
                fragment2.setArguments(bundle)
                add(R.id.container, fragment2)
                addToBackStack(null)
                commit()
            }
        }

    }

}