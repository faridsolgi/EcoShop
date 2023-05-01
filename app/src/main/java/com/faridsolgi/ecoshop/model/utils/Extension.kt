package com.faridsolgi.ecoshop.model.utils

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.math.RoundingMode
import java.text.DecimalFormat


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

fun  TextView.setDrawableTint(color: Int){
    for (drawable in this.compoundDrawables) {
        if (drawable != null) {
            drawable.colorFilter =
                PorterDuffColorFilter(
                    color,
                    PorterDuff.Mode.SRC_IN
                )
        }
    }
}
fun roundOffDecimal(number: Float?): Float? {
    try {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toFloat()
    }catch (e:Exception){
        e.printStackTrace()
        return number
    }
}

