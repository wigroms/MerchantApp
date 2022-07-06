package com.example.mockingmerchantapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.mockingmerchantapp.ApiInterface
import com.example.mockingmerchantapp.MainRepository
import com.example.mockingmerchantapp.ViewModelFactory
import com.example.mockingmerchantapp.databinding.MenuFragmentBinding

class MenuFragment : Fragment() {

    private val binding: MenuFragmentBinding by lazy { MenuFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = MenuFragment()
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
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(MainRepository(ApiInterface.ApiClient.apiInterface))).get(
                MainViewModel::class.java
            )
        val fragmentManager: FragmentManager? = fragmentManager
        viewModel.ClickHistory(binding.imageView8,fragmentManager)
        viewModel.ClickSale(binding.imageView4,fragmentManager)
        viewModel.ClickSetting(binding.imageButton,fragmentManager)
    }

}