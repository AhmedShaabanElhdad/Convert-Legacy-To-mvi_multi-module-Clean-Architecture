package com.example.productfeature.productlist

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.entity.Product
import com.example.entity.Profile
import com.example.productfeature.R
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProductsListFragment : Fragment(R.layout.fragment_products_list){

    var productsListRV: RecyclerView? = null
    var productsListAdapter: ProductsAdapter? = null
    var profile: Profile? = null


    private val args: ProductsListFragmentArgs by navArgs()
    private val viewModel: GetProductViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getProfileData()

        initalization()

        initObservers()
    }

    private fun initalization() {
        val userName: TextView = requireView().findViewById(R.id.username_tv)
        val phoneNumber: TextView = requireView().findViewById(R.id.phone_number_tv)
        val email: TextView = requireView().findViewById(R.id.email_tv)
        val userIV: ImageView = requireView().findViewById(R.id.user_iv)
        val logoutIV: ImageView = requireView().findViewById(R.id.logoutIV)
        productsListRV = requireView().findViewById(R.id.products_list_rv)

        logoutIV.setOnClickListener {
            requireActivity().finish()
        }

        val mLayoutManager = LinearLayoutManager(requireContext().applicationContext)
        productsListRV?.layoutManager = mLayoutManager


        Glide.with(this).load(profile?.image).into(userIV)
        userName.text = profile?.name
        phoneNumber.text = profile?.phone
        email.text = profile?.email
    }

    private fun getProfileData() {
        val gson = Gson()
        profile = gson.fromJson(args.profile, Profile::class.java)
    }


    fun saveList(productsList: List<Product>) {
        productsListAdapter = ProductsAdapter(requireContext(), productsList){
            val action = ProductsListFragmentDirections.actionProductsListFragmentToProductDetailsActivity(it)
            findNavController().navigate(action)
        }
        productsListRV!!.adapter = productsListAdapter
    }



    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (it.GetProductViewState) {
                    is GetProductContract.GetProductViewState.Idle -> {

//                        hideLoading()
                    }
                    is GetProductContract.GetProductViewState.Loading -> {
                        showToast("Loading")
//                        showLoading()
                    }
                    is GetProductContract.GetProductViewState.Success -> {
                        saveList(it.GetProductViewState.products)
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is GetProductContract.GetProductEffect.Error -> {
//                        hideLoading()
                        showToast("Error")
                    }
                }
            }
        }
    }


    private fun showToast(error: String) {
        Toast.makeText(requireContext(),error, Toast.LENGTH_LONG).show()
    }
}