package com.example.productfeature.productdetails

import android.os.Bundle
import android.widget.TextView
import com.arindicatorview.ARIndicatorView
import com.example.productfeature.productdetails.ImagesAdapter
import com.example.productfeature.R
import android.os.Parcelable
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.Product

class ProductDetailsActivity : Fragment(R.layout.activity_product_details) {

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
        val product:Product? = null

        back.setOnClickListener { findNavController().popBackStack() }


        description.text = product!!.deal_description
        title.text = product.name_ar
        description.movementMethod = ScrollingMovementMethod()
        price.text = "كاش" + "           " + product.price + "جنيه"
        val imagesAdapter: ImagesAdapter = ImagesAdapter(requireContext(), product.images)
        imagesListRV.adapter = imagesAdapter
        indicatorView.attachTo(imagesListRV, true)
    }
}