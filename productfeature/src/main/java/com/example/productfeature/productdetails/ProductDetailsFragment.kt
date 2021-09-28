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
import com.example.productfeature.productlist.ProductsListFragmentArgs

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private val args: ProductDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val description: TextView = view.findViewById(R.id.product_description_tv)
        val title: TextView = view.findViewById(R.id.product_title_tv)
        val back: Button = view.findViewById(R.id.materialButton)
        val price: TextView = view.findViewById(R.id.product_price_tv)
        val imagesListRV: RecyclerView = view.findViewById(R.id.product_images_banner)
        val indicatorView: ARIndicatorView = view.findViewById(R.id.ar_indicator)



        //todo get data from args
//        val bundle = intent.getBundleExtra("PARCELABLE")
//        val product = bundle!!.getParcelable<Parcelable>("ITEM") as Product?
        val product:Product = args.product

        back.setOnClickListener { findNavController().popBackStack() }


        description.text = product.deal_description
        title.text = product.name_ar
        description.movementMethod = ScrollingMovementMethod()
        price.text = "كاش" + "           " + product.price + "جنيه"
        val imagesAdapter: ImagesAdapter = ImagesAdapter(requireContext(), product.images)
        imagesListRV.adapter = imagesAdapter
        indicatorView.attachTo(imagesListRV, true)
    }
}