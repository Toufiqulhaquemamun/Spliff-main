package com.mamun.spliff.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamun.spliff.R
import com.mamun.spliff.model.Product
import com.mamun.spliff.ui.gallery.GalleryFragment.Companion.GROUP
import com.mamun.spliff.utils.DatabaseHelper


class ProdGroupFragment : Fragment() , ProductAdapter.ProductItemListener {


    private var prodListView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var root : View? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_prod_group, container, false)
        root= view
        prodListView = view.findViewById(R.id.product_list)

        setRecyclerView()
        // Inflate the layout for this fragment
        return view
    }

    private fun setRecyclerView() {

        var group = ""
        val bundle = arguments
        if (bundle != null) {
            group = bundle.getString(GROUP).toString()
        }
        val db = DatabaseHelper(context)
        val prodList = db.getProductInfoByGroup(group)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        prodListView!!.layoutManager = mLayoutManager
        val adapter = ProductAdapter(prodList,this)
        prodListView!!.removeAllViews()
        prodListView!!.setHasFixedSize(true)
        prodListView!!.itemAnimator = DefaultItemAnimator()
        prodListView!!.adapter = adapter

    }

    override fun onProductSelected(product: Product?) {
        val details = ProductDetailsFragment()
//        if (product != null) {
//            details.init(
//                product.img,
//                product.name,
//                product.desc,
//                product.price
//            )
//        }
        val bundle = bundleOf("img" to product!!.img)

        root?.let { Navigation.findNavController(it).navigate(R.id.nav_prod_detail,bundle) }

    }


}