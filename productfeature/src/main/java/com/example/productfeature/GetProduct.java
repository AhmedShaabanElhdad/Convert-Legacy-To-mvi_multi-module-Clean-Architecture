package com.example.productfeature;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.data.response.ProductsList;
import com.example.productfeature.productlist.GetProductListener;
import com.example.productfeature.productlist.ProductsAdapter;
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
