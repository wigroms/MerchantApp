package com.example.mockingmerchantapp.ui.main

import android.os.Bundle
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
import com.example.mockingmerchantapp.databinding.MainFragmentBinding
import com.example.mockingmerchantapp.databinding.SettingFragmentBinding

class SettingFragment : Fragment() {
    private val binding: SettingFragmentBinding by lazy { SettingFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = SettingFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
     //   viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(MainRepository(ApiInterface.ApiClient.apiInterface))).get(
                MainViewModel::class.java)
        val fragmentManager: FragmentManager? = fragmentManager
        viewModel.ClickSale(binding.buttonSale,fragmentManager)


    }

}