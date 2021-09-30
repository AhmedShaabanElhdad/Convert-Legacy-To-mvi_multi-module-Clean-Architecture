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
import com.example.productfeature.databinding.FragmentProductsListBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProductsListFragment : Fragment(R.layout.fragment_products_list){

    private var productsListAdapter: ProductsAdapter? = null


    private val args: ProductsListFragmentArgs by navArgs()
    private val viewModel: GetProductViewModel by viewModels()

    lateinit var binding: FragmentProductsListBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProductsListBinding.bind(view)

        initalization()

        initObservers()
    }

    private fun initalization() {
        binding.logoutIV.setOnClickListener {
            requireActivity().finish()
        }

        val mLayoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.productsListRv.layoutManager = mLayoutManager

        bindPRofileData()
    }

    private fun bindPRofileData() {
        val profile = getProfileData()
        profile?.let {
            Glide.with(this).load(it.image).into(binding.userIv)
            binding.usernameTv.text = it.name
            binding.phoneNumberTv.text = it.phone
            binding.emailTv.text = it.email
        }

    }

    private fun getProfileData(): Profile? {
        val gson = Gson()
        return gson.fromJson(args.profile, Profile::class.java)
    }


    fun saveList(productsList: List<Product>) {
        productsListAdapter = ProductsAdapter(productsList){
            val action = ProductsListFragmentDirections.actionProductsListFragmentToProductDetailsActivity(it)
            findNavController().navigate(action)
        }
        binding.productsListRv.adapter = productsListAdapter
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