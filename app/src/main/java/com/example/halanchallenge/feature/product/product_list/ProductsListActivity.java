package com.example.halanchallenge.feature.product.product_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.halanchallenge.R;
import com.example.halanchallenge.model.GetProduct;
import com.example.halanchallenge.response.LoginResponse;
import com.example.halanchallenge.response.ProductsList;
import com.google.gson.Gson;

public class ProductsListActivity extends AppCompatActivity implements GetProductListener {

    String response;

    TextView userName, phoneNumber, email;
    RecyclerView productsListRV;
    ImageView userIV,logoutIV;

    LoginResponse loginResponse;
    ProductsList productsList;

    ProductsAdapter productsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            response = bundle.getString("RESPONSE");
        }

        Gson gson = new Gson();
        loginResponse = gson.fromJson(response, LoginResponse.class);

        userName = findViewById(R.id.username_tv);
        phoneNumber = findViewById(R.id.phone_number_tv);
        email = findViewById(R.id.email_tv);
        userIV= findViewById(R.id.user_iv);
        logoutIV = findViewById(R.id.logoutIV);

        Glide.with(this).load(loginResponse.profile.image).into(userIV);

        productsListRV = findViewById(R.id.products_list_rv);

        logoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        productsListRV.setLayoutManager(mLayoutManager);

        //todo move to model in another function
        GetProduct productModel = new GetProduct();
        productModel.getProduct(loginResponse.token,getApplicationContext(),this);

        userName.setText(loginResponse.profile.name);
        phoneNumber.setText(loginResponse.profile.phone);
        email.setText(loginResponse.profile.email);


    }


    @Override
    public void saveList(ProductsList list) {
        this.productsList = list;
        productsListAdapter = new ProductsAdapter(getBaseContext(), productsList.products);
        productsListAdapter.notifyDataSetChanged();
        productsListRV.setAdapter(productsListAdapter);
        productsListAdapter.setClickListener((view, position) -> {

        });
    }
}