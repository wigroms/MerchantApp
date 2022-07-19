package com.example.mockingmerchantapp

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mockingmerchantapp.ModelClass.Transactions
import com.example.mockingmerchantapp.ModelClass.VoidPaymentRequest
import com.example.mockingmerchantapp.ui.main.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class DataAdapter(private val mList: List<Transactions>) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    var transactions = mutableListOf<Transactions>()
    private var removePosition: Int = 0
    private lateinit var removeItem: Any
    //  var setIconDelete

    fun setTransactionList(ts: List<Transactions>) {
        this.transactions = ts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_recycle_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textDate.text = ItemsViewModel.when_.split("T")[1].split("+")[0]
        if (ItemsViewModel.sof.equals("kexwallet")) {
            holder.textView.text = "Kerry Wallet"
        } else if (ItemsViewModel.sof.equals("rabbitwallet")) {
            holder.textView.text = "Rabbit Wallet"
        } else {
            holder.textView.text = ItemsViewModel.sof
        }
        holder.textPrice.text = ItemsViewModel.amount.toString()
        holder.textTxn.text = ItemsViewModel.ref

    }

    override fun getItemCount(): Int {
        //Log.w("mlist",""+mList.size)
        return mList.size
    }

    fun DeleteItem(position: Int) {
        removePosition = position
        removeItem = transactions[position]
        transactions.removeAt(position)
        notifyItemRemoved(position)

        /*   Snackbar.make(itemView,"$removeItem Deleted",Snackbar.LENGTH_LONG).setAction("UNDO"){
               transactions.add(removePosition, removeItem as Transactions)
               notifyItemRemoved(removePosition)
           }*/
    }

    fun SwipeGesture(viewModel: MainViewModel,context:Context): ItemTouchHelper.SimpleCallback {

        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return true
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {
                setDeletedIcon( c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive,context)

                super.onChildDraw(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        val position = viewHolder.adapterPosition
                        Log.d("position", "test")
                        Log.d("position",
                            " " + transactions[position].ref)
                        val df = SimpleDateFormat("HHmmssZ", Locale.US)
                        val cals
                                : Calendar = Calendar.getInstance()
                        val currentDate
                                : String = df.format(cals.getTime())
                        var requestVoid = VoidPaymentRequest("55" + currentDate,
                            "M299",
                            "1",
                            transactions[position].ref)
                        viewModel.getVoidPayment(requestVoid)

                        DeleteItem(position)
                        notifyItemChanged(position)
                       // notifyItemRemoved(position)
                       // viewModel.getTransactionHistory(currentDate)

                }
                /*  .DeleteItem(viewHolder.adapterPosition)
                  .notifyItemRemoved(viewHolder.adapterPosition)*/

            }
        return itemTouchHelperCallback
    }

    private fun setDeletedIcon(   c: Canvas,
                                  recyclerView: RecyclerView,
                                  viewHolder: RecyclerView.ViewHolder,
                                  dX: Float,
                                  dY: Float,
                                  actionState: Int,
                                  isCurrentlyActive: Boolean,
                                  context : Context){

        var mClearPaint : Paint = Paint()
        mClearPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))
        var mBackground : GradientDrawable = GradientDrawable()
        var backgroundColor = Color.parseColor("#b80f0a")
        var deleteDrawble : Drawable = ContextCompat.getDrawable(context,R.drawable.ic_trash)!!
        var intrinsicsWidth = deleteDrawble.intrinsicWidth
        var intrinsicsHeight = deleteDrawble.intrinsicHeight

        var isCancelled = dX == 0.0f && !isCurrentlyActive

        if(isCancelled){
            c.drawRect(viewHolder.itemView.right + dX, viewHolder.itemView.top.toFloat(),viewHolder.itemView.right.toFloat(),viewHolder.itemView.bottom.toFloat(),mClearPaint)
        }
        mBackground.cornerRadius = 20f
        mBackground.setColor(backgroundColor)
        mBackground.setBounds(viewHolder.itemView.right + dX.toInt(), viewHolder.itemView.top,viewHolder.itemView.right,viewHolder.itemView.bottom)
        mBackground.draw(c)

        var deleteIconTop = viewHolder.itemView.top + (viewHolder.itemView.height - intrinsicsHeight) / 2
        var deleteIconMargin = (viewHolder.itemView.height - intrinsicsHeight) / 2
        var deleteIconLeft = viewHolder.itemView.right - deleteIconMargin - intrinsicsWidth
        var deleteIconRight = viewHolder.itemView.right - deleteIconMargin
        var deletedIconBottom = deleteIconTop + intrinsicsHeight

        deleteDrawble.setBounds(deleteIconLeft,deleteIconTop,deleteIconRight,deletedIconBottom)
        deleteDrawble.draw(c)


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


