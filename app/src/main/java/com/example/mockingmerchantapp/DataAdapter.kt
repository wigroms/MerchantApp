package com.example.mockingmerchantapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.mockingmerchantapp.ModelClass.TransactionInquiry
import com.example.mockingmerchantapp.ModelClass.Transactions

class DataAdapter (private val mList: List<Transactions>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    var transactions = mutableListOf<Transactions>()

    fun setTransactionList(ts: List<Transactions>) {
        this.transactions = ts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_recycle_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DataAdapter.ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textDate.text = ItemsViewModel.when_.split("T")[1].split("+")[0]
        if(ItemsViewModel.sof.equals("kexwallet")) {
            holder.textView.text = "Kerry Wallet"
        }else{
            holder.textView.text = ItemsViewModel.sof
        }
        holder.textPrice.text = ItemsViewModel.amount.toString()
        holder.textTxn.text = ItemsViewModel.ref
    }

    override fun getItemCount(): Int {
        //Log.w("mlist",""+mList.size)
        return mList.size
    }


    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
  /*      val textToday: TextView = itemView.findViewById(R.id.textView23)
        val textCount: TextView = itemView.findViewById(R.id.textView25)
        val textTotal: TextView = itemView.findViewById(R.id.textView24)*/
        //  val imageView: ImageView = itemView.findViewById(R.id.imageview)

        val textView: TextView = itemView.findViewById(R.id.textView10)
        val textPrice: TextView = itemView.findViewById(R.id.textView8)
        val textDate: TextView = itemView.findViewById(R.id.textView5)
        val textTxn: TextView = itemView.findViewById(R.id.textView9)
    }


}
