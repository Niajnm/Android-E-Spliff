package com.example.e_itmedi

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_itmedi.CustomAdapter.MyViewHolder
import com.example.e_itmedi.Database.DataResponse
import com.squareup.picasso.Picasso
import java.util.*

class CustomAdapter(
    var context: Context,
    var rdata: ArrayList<DataResponse>,
    val cellClickListener: CellClickListener,
    val dialogListener: DialogListener
) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.sample_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val dataTitle = rdata[position].tt.toString()
        val dataPrice = rdata[position].pp.toString()
        val dataDesc = rdata[position].dd.toString()
        val dataID = rdata[position].id.toString()
        val dataImg: String? = rdata[position].img

        holder.viewTitle.text = rdata[position].tt
        holder.viewDesc.text = rdata[position].dd
        holder.viewPrice.text = "$" + rdata[position].pp

        Picasso.get().load(dataImg).into(holder.imageViewLogo)

        holder.itemView.setOnClickListener {

            cellClickListener.onCellClickListener(dataTitle, dataPrice, dataDesc, dataID, dataImg)
        }

        holder.cartButton.setOnClickListener(View.OnClickListener {


            val builder1 = AlertDialog.Builder(context)
            val layoutInflater = LayoutInflater.from(context)
            val dialogView = layoutInflater.inflate(R.layout.dialog_cart_layout, null)
            builder1.setView(dialogView)
            builder1.setCancelable(true)

            val alert11: AlertDialog = builder1.create()
            alert11.show()

            var dialogViewIncrement: ImageButton
            var dialogViewDecrement: ImageButton
            var dialogtextViewCount: TextView
            var dialogCartButton: Button

            dialogViewIncrement = dialogView.findViewById(R.id.dialogButton_increment_id)
            dialogViewDecrement = dialogView.findViewById(R.id.dialogButton_Decrement_id)
            dialogtextViewCount = dialogView.findViewById(R.id.dialogTextview_counter_id)
            dialogCartButton = dialogView.findViewById(R.id.dialogButton_cart_id)

            var count = 1
            dialogtextViewCount.text = count.toString()

            dialogViewIncrement.setOnClickListener({

                count++
                dialogtextViewCount.text = count.toString()
            })
            dialogViewDecrement.setOnClickListener({

                if (count > 1) {

                    count--
                    dialogtextViewCount.text = count.toString()
                }
            })

            dialogCartButton.setOnClickListener({
                val dataID = rdata[position].id.toString()
                val dataPrice = rdata[position].pp.toString()

                dialogListener.onDialogListener(
                    dataTitle,
                    dataPrice,
                    dataDesc,
                    dataID,
                    count,
                    dataImg
                )
            })
        })
    }

    override fun getItemCount(): Int {
        return rdata.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewTitle: TextView
        var viewPrice: TextView
        var viewDesc: TextView
        var cartButton: ImageButton
        var imageViewLogo: ImageView

        init {
            viewTitle = itemView.findViewById(R.id.textView_title)
            viewPrice = itemView.findViewById(R.id.textViewCart_price)
            viewDesc = itemView.findViewById(R.id.textView_desc)
            cartButton = itemView.findViewById(R.id.cartButton_id)
            imageViewLogo = itemView.findViewById(R.id.sampleImg_id)
        }
    }
}