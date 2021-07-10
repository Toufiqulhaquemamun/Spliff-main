package com.mamun.spliff.ui.gallery

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.mamun.spliff.R
import com.mamun.spliff.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter(private val prodList: List<Product>?
,private val listener: ProductItemListener) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    interface ProductItemListener {
        fun onProductSelected(product: Product?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_prod_info, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.MyViewHolder, position: Int) {
        val prod = prodList!![position]
        if (prod != null) {

            Picasso.get().load(prod.img).into(holder.prodImg);
            holder.prodName.text = prod.name
            holder.unitPrize.text = "$"+prod.price
            holder.addFav.setOnClickListener {
                Log.d("TAG", "onBindViewHolder: added to cart")
            }
        }
    }

    override fun getItemCount(): Int {
       return prodList?.size ?: 0
    }

   inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

       lateinit var prodImg: ImageView
       lateinit var prodName: TextView
       lateinit var unitPrize: TextView
       lateinit var addFav: ImageButton
       init {
           prodImg = itemView.findViewById(R.id.imgLink)
           prodName = itemView.findViewById(R.id.prodName)
           unitPrize = itemView.findViewById(R.id.prodPrice)
           addFav = itemView.findViewById(R.id.addfav)

           itemView.setOnClickListener {
               listener.onProductSelected(
                   prodList!![adapterPosition])
           }
       }

    }


}