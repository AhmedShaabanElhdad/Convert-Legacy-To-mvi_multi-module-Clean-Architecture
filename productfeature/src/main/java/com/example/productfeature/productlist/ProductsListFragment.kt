package com.example.productfeature.productlist

import com.example.data.response.ProductsList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.productfeature.R
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.response.LoginResponse
import com.bumptech.glide.Glide
import com.example.productfeature.GetProduct
import com.example.productfeature.productlist.ProductsAdapter.ItemClickListener
import com.google.gson.Gson

class ProductsListFragment : Fragment(R.layout.fragment_products_list), GetProductListener {

    var productsList: ProductsList? = null
    var productsListRV: RecyclerView? = null
    var productsListAdapter: ProductsAdapter? = null
    var response: String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginResponse: LoginResponse

        //todo handle the result from navigation args
//        val bundle = intent.extras
//        if (bundle != null) {
//            response = bundle.getString("RESPONSE")
//        }


        val gson = Gson()
        loginResponse = gson.fromJson(response, LoginResponse::class.java)
        val userName: TextView = view.findViewById(R.id.username_tv)
        val phoneNumber: TextView = view.findViewById(R.id.phone_number_tv)
        val email: TextView = view.findViewById(R.id.email_tv)
        val userIV: ImageView = view.findViewById(R.id.user_iv)
        val logoutIV: ImageView = view.findViewById(R.id.logoutIV)
        Glide.with(this).load(loginResponse.profile.image).into(userIV)
        productsListRV = view.findViewById(R.id.products_list_rv)
        logoutIV.setOnClickListener {
//            requireActivity().finish()
            findNavController().popBackStack()
        }
        val mLayoutManager = LinearLayoutManager(requireContext().applicationContext)
        productsListRV?.layoutManager = mLayoutManager

        //todo move to model in another function
        val productModel = GetProduct()
        productModel.getProduct(loginResponse.token, requireContext().applicationContext, this)
        userName.text = loginResponse.profile.name
        phoneNumber.text = loginResponse.profile.phone
        email.text = loginResponse.profile.email

    }


    override fun saveList(list: ProductsList) {
        productsList = list
        productsListAdapter = ProductsAdapter(requireContext(), productsList!!.products)
        productsListAdapter!!.notifyDataSetChanged()
        productsListRV!!.adapter = productsListAdapter
        productsListAdapter!!.setClickListener(object : ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {

            }
        })
    }
}