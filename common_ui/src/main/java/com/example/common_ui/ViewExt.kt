package com.example.common_ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImagesWithGlideExt(url: String) {
    Glide.with(this)
        .load(url)
//        .centerCrop()
        .error(R.drawable.place_holder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.place_holder)
        .into(this)
}



fun Fragment.getLoadingDialog(context: Context): LoadingDialog {
    val progressDialog = LoadingDialog(context)
    progressDialog.show()
    if (progressDialog.window != null) {
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    progressDialog.setCancelable(true)
    progressDialog.setCanceledOnTouchOutside(true)
    return progressDialog
}


fun Fragment.hideKeyboard() {
    val view = requireActivity().currentFocus
    if (view != null) {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


fun Fragment.showToast(msg:String){
    Toast.makeText(requireContext(),msg,Toast.LENGTH_LONG).show()
}


