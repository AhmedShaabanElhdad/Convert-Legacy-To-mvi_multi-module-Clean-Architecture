package com.example.halanchallenge.model;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.halanchallenge.feature.product.product_list.GetProductListener;
import com.example.halanchallenge.feature.product.product_list.ProductsAdapter;
import com.example.halanchallenge.response.ProductsList;
import com.google.gson.Gson;

import org.json.JSONObject;

// Call async LoginTask
public class GetProduct {
    ProductsList productsList;
    ProductsAdapter productsListAdapter;

    public void getProduct(String token, Context context, GetProductListener listener) {
        AndroidNetworking.initialize(context.getApplicationContext());
        AndroidNetworking.get("https://assessment-sn12.halan.io/products")
                .addHeaders("Authorization", "Bearer " + token)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        productsList = new Gson().fromJson(String.valueOf(response), ProductsList.class);
                        listener.saveList(productsList);
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("FastError", error.getMessage());
                    }
                });
    }
}
