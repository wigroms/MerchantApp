package com.example.mockingmerchantapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mockingmerchantapp.*
import com.example.mockingmerchantapp.ModelClass.Transactions
import com.example.mockingmerchantapp.databinding.TransactionHistoryFragmentBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HistoryFragment : Fragment() {

    val binding: TransactionHistoryFragmentBinding by lazy { TransactionHistoryFragmentBinding.inflate(layoutInflater) }


    companion object {
        fun newInstance() = HistoryFragment()
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
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(ApiInterface.ApiClient.apiInterface))).get(MainViewModel::class.java)
        viewModel.getTransactionHistory()
        //var adapter = DataAdapter()

        val fragmentManager: FragmentManager? = fragmentManager
        viewModel.ClickSale(binding.buttonSale,fragmentManager)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

       /* val data = ArrayList<ItemsViewModel>()
        for (i in 1..20) {
            if(i % 2 == 0) {
                data.add(ItemsViewModel("10:30:00","Rabbit Wallet","50"))
            }else{
                data.add(ItemsViewModel("12:30:00","Kerry Wallet","120"))
            }
        }*/

        val trans = ArrayList<Transactions>()
        for (i in 1..3) {
            if(i % 2 == 0) {
                trans.add(Transactions(ref="CB1654822554000152", when_="2022-06-10T07:55:54+07:00", amount=100, status="success", gw_ref="1654825031000158", gw_when="2022-06-10T08:37:12+07:00", merchant_id="M257", sof="kexwallet", terminal_id="ITS61", source_origin="site-caribbean", source_txn_ref="CB1654822554000152", gw_account="AC287", invoice=""))
            }else{
              //  trans.add(Transactions("12:30:00","Kerry Wallet",120,))
            }
        }

        // This will pass the ArrayList to our Adapter
        //val adapter = CustomAdapter(data)

        binding.recyclerView.setVisibility(View.INVISIBLE);

        var adapter = DataAdapter(trans)
        adapter?.notifyDataSetChanged()
        // Setting the Adapter with the recyclerview
        binding.recyclerView.adapter = adapter

        viewModel.transactionslist.observe(viewLifecycleOwner, Observer {
            Log.d("observe history", "onCreate: $it")
            binding.recyclerView.setVisibility(View.VISIBLE);
            adapter?.setTransactionList(it)
            var adapter = DataAdapter(it)
            binding.recyclerView.adapter = adapter
         })

        viewModel.transactionInquiry.observe(viewLifecycleOwner, Observer {
            Log.d("observe history box", "onCreate: $it")
            // val Date: DateFormat = DateFormat.getDateInstance(DateFormat.LONG)
            val df = SimpleDateFormat("d MMMM yyyy", Locale.US)
            val cals: Calendar = Calendar.getInstance()
            val currentDate: String = df.format(cals.getTime())

            binding.textView23.text = currentDate
            binding.textView24.text = String.format("%,d",viewModel.getTransactionTotalAmount())
            binding.textView25.text = it.data.count.toString() + " transaction"

        })

    }
}
