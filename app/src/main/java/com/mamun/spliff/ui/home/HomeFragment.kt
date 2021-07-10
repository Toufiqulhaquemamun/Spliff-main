package com.mamun.spliff.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.mamun.spliff.R
import com.mamun.spliff.databinding.FragmentHomeBinding
import com.mamun.spliff.model.Product
import com.mamun.spliff.utils.DatabaseHelper
import java.util.ArrayList

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private var offerItem : LinearLayout ? =null
    private var cardItem : LinearLayout ? =null

    private val productsList: MutableList<Product> = ArrayList()

    private var flag: Int = 0;


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        offerItem = root.findViewById(R.id.offerItem)
        cardItem = root.findViewById(R.id.cardItem)
        val db = DatabaseHelper(context)

//       val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        cardItem?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_gallery)
        }
        offerItem?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.nav_gallery)
        }

        if(db.getAllProductGroup()!!.size <= 0)
        {
           additemtoList()
        }
//        additemtoList()
        return root
    }

    private fun additemtoList() {
        flag=1
        var productitem = Product()
        val db = DatabaseHelper(context)

        productitem.prodNo = "1"
        productitem.name = "Indica blend"
        productitem.group = "Flowers"
        productitem.desc = "Lorem Ipsum is a context Lorem Ipsum is a context Lorem Ipsum is a context"
        productitem.price ="20"
        productitem.img = "https://upload.wikimedia.org/wikipedia/commons/2/25/Cauliflower.JPG"
        productsList.add(productitem)

        var productitem2 = Product()
        productitem2.prodNo = "2"
        productitem2.name = "Indica blend"
        productitem2.group = "Flowers"
        productitem2.desc = "Lorem Ipsum is a context Lorem Ipsum is a context Lorem Ipsum is a context"
        productitem2.price ="20"
        productitem2.img = "https://w7.pngwing.com/pngs/844/621/png-transparent-romanesco-broccoli-vegetable-food-broccoli-leaf-vegetable-cooking-grass-thumbnail.png"
        productsList.add(productitem2)

        var productitem3 = Product()
        productitem3.prodNo = "3"
        productitem3.name = "Indica blend"
        productitem3.group = "Flowers"
        productitem3.desc = "Lorem Ipsum is a context Lorem Ipsum is a context Lorem Ipsum is a context"
        productitem3.price ="20"
        productitem3.img = "https://w7.pngwing.com/pngs/844/621/png-transparent-romanesco-broccoli-vegetable-food-broccoli-leaf-vegetable-cooking-grass-thumbnail.png"
        productsList.add(productitem3)
        var productitem7 = Product()
        productitem7.prodNo = "4"
        productitem7.name = "Indica blend"
        productitem7.group = "Flowers"
        productitem7.desc = "Lorem Ipsum is a context Lorem Ipsum is a context Lorem Ipsum is a context"
        productitem7.price ="20"
        productitem7.img = "https://w7.pngwing.com/pngs/534/900/png-transparent-broccoli-organic-food-vegetable-cabbage-sulforaphane-broccoli-leaf-vegetable-food-cabbage-thumbnail.png"

        productsList.add(productitem7)

        var productitem4 = Product()
        productitem4.prodNo = "5"
        productitem4.name = "Indica blend"
        productitem4.group = "Vapes"
        productitem4.desc = "Lorem Ipsum is a context Lorem Ipsum is a context Lorem Ipsum is a context"
        productitem4.price ="20"
        productitem4.img = "https://upload.wikimedia.org/wikipedia/commons/2/25/Cauliflower.JPG"
        productsList.add(productitem4)

        var productitem8 = Product()
        productitem8.prodNo = "6"
        productitem8.name = "Indica blend"
        productitem8.group = "Vapes"
        productitem8.desc = "Lorem Ipsum is a context Lorem Ipsum is a context Lorem Ipsum is a context"
        productitem8.price ="20"
        productitem8.img = "https://upload.wikimedia.org/wikipedia/commons/2/25/Cauliflower.JPG"
        productsList.add(productitem8)

        var productitem5 = Product()
        productitem5.prodNo = "7"
        productitem5.name = "Indica blend"
        productitem5.group = "Extracts"
        productitem5.desc = "Lorem Ipsum is a context Lorem Ipsum is a context Lorem Ipsum is a context"
        productitem5.price ="20"
        productitem5.img = "https://upload.wikimedia.org/wikipedia/commons/2/25/Cauliflower.JPG"
        productsList.add(productitem5)

        var productitem6 = Product()
        productitem6.prodNo = "8"
        productitem6.name = "Indica blend"
        productitem6.desc = "Lorem Ipsum is a context Lorem Ipsum is a context Lorem Ipsum is a context"
        productitem6.group = "Extracts"
        productitem6.price ="20"
        productitem6.img = "https://upload.wikimedia.org/wikipedia/commons/2/25/Cauliflower.JPG"
        productsList.add(productitem6)
        db.addProduct(productsList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}