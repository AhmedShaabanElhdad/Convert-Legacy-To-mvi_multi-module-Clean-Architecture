package com.example.productfeature.deprecated

import com.example.data.response.ProductsList
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.productfeature.R
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.response.LoginResponse
import com.bumptech.glide.Glide
import com.example.entity.Profile
import com.example.productfeature.productlist.ProductsAdapter
import com.example.productfeature.productlist.ProductsListFragmentArgs
import com.example.productfeature.productlist.ProductsListFragmentDirections
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListFragmentDeprecated : Fragment(R.layout.fragment_products_list),
    GetProductListener {

    var productsList: ProductsList? = null
    var productsListRV: RecyclerView? = null
    var productsListAdapter: ProductsAdapterDeprecated? = null
    var profile: Profile? = null


    private val args: ProductsListFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginResponse: LoginResponse


        //todo handle the result from navigation args
//        val bundle = intent.extras
//        if (bundle != null) {
//            response = bundle.getString("RESPONSE")
//        }


        val gson = Gson()
        profile = gson.fromJson(args.profile, Profile::class.java)
        val userName: TextView = view.findViewById(R.id.username_tv)
        val phoneNumber: TextView = view.findViewById(R.id.phone_number_tv)
        val email: TextView = view.findViewById(R.id.email_tv)
        val userIV: ImageView = view.findViewById(R.id.user_iv)
        val logoutIV: ImageView = view.findViewById(R.id.logoutIV)
        Glide.with(this).load(profile?.image).into(userIV)
        productsListRV = view.findViewById(R.id.products_list_rv)
        logoutIV.setOnClickListener {
//            requireActivity().finish()
            findNavController().popBackStack()
        }
        val mLayoutManager = LinearLayoutManager(requireContext().applicationContext)
        productsListRV?.layoutManager = mLayoutManager

        //todo move to model in another function
        val productModel = GetProduct()
        productModel.getProduct("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImFobWVkc2hhYWJhbiIsImltYWdlIjoiaHR0cHM6Ly9pLnBpY3N1bS5waG90b3MvaWQvMTA2Mi81MDkyLzMzOTUuanBnP2htYWM9bzltN3FlVTUxdU9MZlh2ZXBYY1RyazJaUGlTQkpFa2lpT3AtUXZ4amEtayIsIm5hbWUiOiIgINis2YbZitivINil2YXYp9mFIiwiZW1haWwiOiI0ZDU2dGc2eDk3a2dlODdAZ21haWwuY29tIiwicGhvbmUiOiIwMTkxMDE1MTg5MSIsImlhdCI6MTYzMjkxNzAxNywiZXhwIjoxNjMyOTE3OTQ3fQ.IRy348OhI6_kV4IiA5rftuDmrhC7reWYWZ7uknSUZuk",
            requireContext().applicationContext, this)
        userName.text = profile?.name
        phoneNumber.text = profile?.phone
        email.text = profile?.email

    }


    override fun saveList(list: ProductsList) {
        productsList = list
        productsListAdapter = ProductsAdapterDeprecated(requireContext(), productsList!!.products)
        productsListAdapter!!.notifyDataSetChanged()
        productsListRV!!.adapter = productsListAdapter
        productsListAdapter!!.setClickListener(object : ProductsAdapterDeprecated.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {

                val action =
                    ProductsListFragmentDirections.actionProductsListFragmentToProductDetailsActivity(
                        productsList!!.products[position]
                    )
                findNavController().navigate(action)
            }
        })
    }
}