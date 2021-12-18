package com.example.e_itmedi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_itmedi.Database.DataResponse
import com.squareup.picasso.Picasso
import java.util.ArrayList

class CartAdapter(var context: Context, var rdata: ArrayList<DataResponse>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    val ctx = context as CartActivity

    private val TAG = "CartActivity"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.cart_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewTitle.text = rdata[position].tt
        holder.viewDesc.text = rdata[position].dd
        holder.viewPrice.text = "$"+rdata[position].pp
        holder.viewCount.text = rdata[position].cc

        val imgData: String?= rdata[position].img

        Picasso.get().load(imgData).into(holder.imgProduct )

        var count=0
        var afterPrice =rdata[position].pp!!.toInt()
        var negPrice:Int=rdata[position].pp!!.toInt()

        holder.imgPlus.setOnClickListener(View.OnClickListener {
            var productId =rdata[position].id
            count++
            var priceFromDB=rdata[position].cc!!.toInt()
            holder.viewCount.text =(priceFromDB+count).toString()
            var up=rdata[position].pp!!.toInt()/priceFromDB

             afterPrice = afterPrice+up

            holder.viewPrice.text = "$"+afterPrice
            negPrice=afterPrice

            ctx.updateTotalPrice(up,productId)

        })

        holder.imgMinus.setOnClickListener(View.OnClickListener {
            var productId =rdata[position].id

           if ((rdata[position].cc!!.toInt()+count)>1){

               count--

               var u=rdata[position].cc!!.toInt()

               var up=rdata[position].pp!!.toInt()/u

               negPrice =negPrice-up

               afterPrice =negPrice

               val updateCount =(rdata[position].cc!!.toInt() +count).toString()

               holder.viewCount.text = updateCount

               holder.viewPrice.text = "$"+afterPrice

               ctx.updateDecrementTotalPrice(up,productId)
           }



        })

//        holder.itemView.setOnClickListener {
//
// val dataTitle = rdata[position].tt.toString()
//        val dataPrice = rdata[position].pp.toString()
//        val dataDesc= rdata[position].dd.toString()
//        val dataID= rdata[position].id.toString()
//
//
//            //cellClickListener.onCellClickListener(dataTitle, dataPrice, dataDesc)
////            cellClickListener.onCellClickListener(dataTitle, dataPrice, dataDesc,dataID)
//        }
    }



    override fun getItemCount(): Int {
        return rdata.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var viewTitle: TextView
        var viewPrice: TextView
        var viewDesc: TextView
        var viewCount: TextView
        var imgPlus: ImageButton
        var imgMinus: ImageButton
        var imgProduct: ImageView

        init {
            viewTitle = itemView.findViewById(R.id.textViewCart_title)
            viewPrice = itemView.findViewById(R.id.textViewCart_price)
            viewDesc = itemView.findViewById(R.id.textViewCart_type)
            viewCount = itemView.findViewById(R.id.textView_CartCount)
            imgPlus = itemView.findViewById(R.id.imgView_cartPlus)
            imgMinus = itemView.findViewById(R.id.imgview_cartMinus)
            imgProduct = itemView.findViewById(R.id.cartproductImg_id)
        }
    }
}