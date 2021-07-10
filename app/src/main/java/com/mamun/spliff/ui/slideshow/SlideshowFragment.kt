package com.mamun.spliff.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mamun.spliff.R
import com.mamun.spliff.databinding.FragmentSlideshowBinding
import com.mamun.spliff.model.Product
import com.mamun.spliff.ui.gallery.ProductAdapter
import com.mamun.spliff.utils.DatabaseHelper

class SlideshowFragment : Fragment() {

    private lateinit var slideshowViewModel: SlideshowViewModel
    private var _binding: FragmentSlideshowBinding? = null
    private var prodListView: RecyclerView? = null
    private var button: Button?= null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        prodListView = root.findViewById(R.id.favList)
        button = root.findViewById(R.id.btnfinish)


        button?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_prod_finish)
        }

        setRecyclerView();
        return root
    }

    private fun setRecyclerView() {
        val db = DatabaseHelper(context)
        val prodList: List<Product> = db.queryFavProd()
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        prodListView!!.layoutManager = mLayoutManager
        val adapter = ProductFabAdapter(prodList)
        prodListView!!.removeAllViews()
        prodListView!!.setHasFixedSize(true)
        prodListView!!.itemAnimator = DefaultItemAnimator()
        prodListView!!.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}