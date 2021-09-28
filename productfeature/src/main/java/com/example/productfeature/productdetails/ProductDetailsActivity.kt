package com.example.productfeature.productdetails

import android.os.Bundle
import android.widget.TextView
import com.arindicatorview.ARIndicatorView
import com.example.productfeature.productdetails.ImagesAdapter
import com.example.productfeature.R
import android.os.Parcelable
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.entity.Product

class ProductDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        val description: TextView = findViewById(R.id.product_description_tv)
        val title: TextView = findViewById(R.id.product_title_tv)
        val back: Button = findViewById(R.id.materialButton)
        val price: TextView = findViewById(R.id.product_price_tv)
        val imagesListRV: RecyclerView = findViewById(R.id.product_images_banner)
        val indicatorView: ARIndicatorView = findViewById(R.id.ar_indicator)
        val bundle = intent.getBundleExtra("PARCELABLE")
        val product = bundle!!.getParcelable<Parcelable>("ITEM") as Product?
        back.setOnClickListener { finish() }
        description.text = product!!.deal_description
        title.text = product.name_ar
        description.movementMethod = ScrollingMovementMethod()
        price.text = "كاش" + "           " + product.price + "جنيه"
        val imagesAdapter: ImagesAdapter = ImagesAdapter(this, product.images)
        imagesListRV.adapter = imagesAdapter
        indicatorView.attachTo(imagesListRV, true)
    }
}