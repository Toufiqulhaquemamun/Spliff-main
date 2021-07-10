package com.mamun.spliff.ui.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.mamun.spliff.R
import com.squareup.picasso.Picasso


class ProductDetailsFragment : Fragment() {

    var image : String ? = null
    var name : String ? = null
    var desc : String ? = null
    var price : String ? = null
    var imgageView : ImageView? =null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        image = arguments?.getString("img")
        var root : View  = inflater.inflate(R.layout.fragment_product_details, container, false)
        imgageView =  root.findViewById(R.id.img_details)

        Picasso.get().load(image).into(imgageView);
        return root
    }

}