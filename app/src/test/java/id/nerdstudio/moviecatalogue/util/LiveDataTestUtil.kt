package id.nerdstudio.moviecatalogue.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

inline fun <reified T> LiveData<T>.getData(): T {
    val data = arrayOfNulls<T>(1)
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(obj: T) {
            data[0] = obj
            latch.countDown()
            removeObserver(this)
        }
    }

    observeForever(observer)

    try {
        latch.await(2, TimeUnit.SECONDS)
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

    return data[0] as T
}
