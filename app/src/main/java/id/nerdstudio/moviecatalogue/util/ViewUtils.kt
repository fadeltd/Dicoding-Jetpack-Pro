package id.nerdstudio.moviecatalogue.util

import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import com.facebook.shimmer.ShimmerFrameLayout
import id.nerdstudio.moviecatalogue.R

fun ImageView.setImagePoster(name: String?) {
    val resId = resources.getIdentifier(name, "raw", context.packageName)

    if (resId != 0) {
        setImageBitmap(BitmapFactory.decodeStream(resources.openRawResource(resId)))
    } else {
        setImageResource(R.drawable.img_default)
    }
}

fun ShimmerFrameLayout.startShimmer() {
    if (visibility == View.VISIBLE) {
        this.startShimmerAnimation()
    }
}
