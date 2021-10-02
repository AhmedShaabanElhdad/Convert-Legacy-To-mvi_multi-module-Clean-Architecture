package com.example.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name_ar: String?,
    val deal_description: String?,
    val brand: String?,
    val image: String?,
    val name_en: String?,
    val price: Int,
    val images: List<String>?
):Parcelable