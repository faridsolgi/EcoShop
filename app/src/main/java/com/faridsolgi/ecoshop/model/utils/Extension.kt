package com.faridsolgi.ecoshop.model.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


fun RecyclerView.initRecycler(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}

fun AppCompatActivity.navHost(view: View): NavHostFragment =
    this.supportFragmentManager.findFragmentById(view.id) as NavHostFragment


 fun Fragment.alertUser(
    view: View = getView()!!,
    msg: String,
    duration: Int = Snackbar.LENGTH_LONG
) {

    Snackbar.make(view, msg, duration).show()
}