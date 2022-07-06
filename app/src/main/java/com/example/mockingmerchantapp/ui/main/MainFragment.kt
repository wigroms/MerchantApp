package com.example.mockingmerchantapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mockingmerchantapp.*
import com.example.mockingmerchantapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private val binding: MainFragmentBinding by lazy { MainFragmentBinding.inflate(layoutInflater) }
    private val retrofitService = ApiInterface.ApiClient;

    companion object {
        fun newInstance() = MainFragment()
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
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel =
            ViewModelProvider(this,
                ViewModelFactory(MainRepository(retrofitService.apiInterface))).get(
                MainViewModel::class.java
            )// Use Model because ViewModelFactory
        //  viewModel.getTransactionHistory()

        val fragmentManager: FragmentManager? = fragmentManager
        viewModel.ClickMenu(binding.buttonMenu, fragmentManager)
        viewModel.ClickHistory(binding.buttonHistory, fragmentManager)
        // TODO: Use the ViewModel
        binding.buttonOK.setOnClickListener {

            var str: String? = viewModel.GetAmountValueString()
            Log.w("str", "" + str)
            if (str?.isNotEmpty() == true && str?.isNotBlank() ) {
                val fragment2 = Payment_Method_Fragment()
                val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.apply {
                    setCustomAnimations(
                        R.anim.fade_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.slide_in,   // popEnter
                        R.anim.slide_out  // popExit
                    )
                    val bundle = Bundle()
                    viewModel.GetAmountValue()?.let { it1 -> bundle.putInt("Amount", it1) }
                    fragment2.setArguments(bundle)
                    replace(R.id.container, fragment2)
                    commit()
                }
            }
        }

        viewModel.setClickBtn(binding)

        viewModel.Amount_Str.observe(viewLifecycleOwner, Observer {
            Log.w("AmountValue", "" + it)
            Log.w("AmountValue", "" +  viewModel.GetAmountValueString())
            // Here we observe livedata
        })

    }

}