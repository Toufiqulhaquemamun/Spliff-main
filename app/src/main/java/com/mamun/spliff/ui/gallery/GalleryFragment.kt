package com.mamun.spliff.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mamun.spliff.R
import com.mamun.spliff.databinding.FragmentGalleryBinding
import com.mamun.spliff.utils.DatabaseHelper

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        const val GROUP = "group"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)


        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

       val tabLayout: TabLayout = root.findViewById(R.id.tabLayout)
       val viewPager: ViewPager = root.findViewById(R.id.view_pager)

        val db = DatabaseHelper(context)
        val prodGroup: List<String?> = ArrayList(db.getAllProductGroup())


        if (!prodGroup.isEmpty()) {
            adapterSetup(tabLayout, viewPager, prodGroup)
            
        } else {
            Toast.makeText(activity, "Please Sync you data first", Toast.LENGTH_SHORT).show()
           
        }

//        val textView: TextView = binding.textGallery
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    private fun adapterSetup(tabLayout: TabLayout, viewPager: ViewPager, prodGroup: List<String?>) {
        val adapter = TabAdapter(
            childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        for (i in prodGroup.indices) {
            val pGroup = if (prodGroup[i] == null || prodGroup[i]!!
                    .trim { it <= ' ' } == ""
            ) "N" else prodGroup[i]!!
            val f = ProdGroupFragment()
            val b = Bundle()
            b.putString(GROUP, prodGroup[i])
            f.arguments = b
            adapter.addFragment(f, pGroup)
        }
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}