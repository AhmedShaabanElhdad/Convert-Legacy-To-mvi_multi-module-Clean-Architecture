package com.example.productfeature.productdetails

import android.os.Bundle
import android.widget.TextView
import com.arindicatorview.ARIndicatorView
import com.example.productfeature.R
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.Product
import com.example.productfeature.databinding.FragmentProductDetailsBinding
import com.example.productfeature.databinding.FragmentProductsListBinding
import com.example.productfeature.productlist.ProductsListFragmentArgs

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private val args: ProductDetailsFragmentArgs by navArgs()

    lateinit var binding: FragmentProductDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductDetailsBinding.bind(view)

        val product:Product = args.product

        binding.materialButton.setOnClickListener { goBack() }

        binding.productDescriptionTv.text = product.deal_description
        binding.productTitleTv.text = product.name_ar
        binding.productDescriptionTv.movementMethod = ScrollingMovementMethod()
        //todo make mapper class from model entity to view entity
        binding.productPriceTv.text = "كاش" + "           " + product.price + "جنيه"
        val imagesAdapter: ImagesAdapter = ImagesAdapter(product.images)
        binding.productImagesBanner.adapter = imagesAdapter
        binding.arIndicator.attachTo(binding.productImagesBanner, true)
    }

    private fun goBack() {
        findNavController().popBackStack()
    }
}