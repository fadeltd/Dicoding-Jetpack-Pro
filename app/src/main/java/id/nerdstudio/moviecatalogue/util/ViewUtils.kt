package id.nerdstudio.moviecatalogue.util

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.Toast
import id.nerdstudio.moviecatalogue.R

fun ImageView.setImagePoster(name: String?) {
    val resId = resources.getIdentifier(name, "raw", context.packageName)

    if (resId != 0) {
        setImageBitmap(BitmapFactory.decodeStream(resources.openRawResource(resId)))
    } else {
        setImageResource(R.drawable.img_default)
    }
}

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, length).show()
}