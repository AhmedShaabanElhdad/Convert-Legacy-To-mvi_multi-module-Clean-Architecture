package com.example.productfeature.productlist

import com.example.data.response.ProductsList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.productfeature.R
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.response.LoginResponse
import com.bumptech.glide.Glide
import com.example.productfeature.GetProduct
import com.example.productfeature.productlist.ProductsAdapter.ItemClickListener
import com.google.gson.Gson

class ProductsListActivity : AppCompatActivity(), GetProductListener {

    var productsList: ProductsList? = null
    var productsListRV: RecyclerView? = null
    var productsListAdapter: ProductsAdapter? = null
    var response: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products_list)
        val loginResponse: LoginResponse
        val bundle = intent.extras
        if (bundle != null) {
            response = bundle.getString("RESPONSE")
        }
        val gson = Gson()
        loginResponse = gson.fromJson(response, LoginResponse::class.java)
        val userName: TextView = findViewById(R.id.username_tv)
        val phoneNumber: TextView = findViewById(R.id.phone_number_tv)
        val email: TextView = findViewById(R.id.email_tv)
        val userIV: ImageView = findViewById(R.id.user_iv)
        val logoutIV: ImageView = findViewById(R.id.logoutIV)
        Glide.with(this).load(loginResponse.profile.image).into(userIV)
        productsListRV = findViewById(R.id.products_list_rv)
        logoutIV.setOnClickListener { finish() }
        val mLayoutManager = LinearLayoutManager(applicationContext)
        productsListRV?.layoutManager = mLayoutManager

        //todo move to model in another function
        val productModel = GetProduct()
        productModel.getProduct(loginResponse.token, applicationContext, this)
        userName.text = loginResponse.profile.name
        phoneNumber.text = loginResponse.profile.phone
        email.text = loginResponse.profile.email
    }

    override fun saveList(list: ProductsList) {
        productsList = list
        productsListAdapter = ProductsAdapter(baseContext, productsList!!.products)
        productsListAdapter!!.notifyDataSetChanged()
        productsListRV!!.adapter = productsListAdapter
        productsListAdapter!!.setClickListener(object : ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {

            }
        })
    }
}