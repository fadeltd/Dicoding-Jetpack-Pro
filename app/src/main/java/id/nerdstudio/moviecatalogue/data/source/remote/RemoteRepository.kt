package id.nerdstudio.moviecatalogue.data.source.remote

import android.os.Handler
import id.nerdstudio.moviecatalogue.data.Item
import id.nerdstudio.moviecatalogue.data.Type
import id.nerdstudio.moviecatalogue.util.EspressoIdlingResource
import id.nerdstudio.moviecatalogue.util.JsonUtils


class RemoteRepository private constructor(private val jsonUtils: JsonUtils) {
    fun getAllItems(
        type: Type,
        onReceived: ((itemResponses: List<Item>) -> Unit)? = null //,
//        onDataNotAvailable: (() -> Unit)? = null
    ) {
        EspressoIdlingResource.increment()
        val handler = Handler()
        handler.postDelayed({
            onReceived?.invoke(jsonUtils.loadItems(type))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

//    fun getFavoriteItems(
//        type: Type,
//        onReceived: (itemResponses: List<Item>) -> Unit,
//        onDataNotAvailable: (() -> Unit)? = null
//    ) {
//        EspressoIdlingResource.increment()
//        val handler = Handler()
//        handler.postDelayed({
//            onReceived(jsonUtils.loadItems(type))
//            EspressoIdlingResource.decrement()
//        }, SERVICE_LATENCY_IN_MILLIS)
//    }

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000
        private var INSTANCE: RemoteRepository? = null

        fun getInstance(utils: JsonUtils): RemoteRepository {
            if (INSTANCE == null) {
                INSTANCE = RemoteRepository(utils)
            }
            return INSTANCE as RemoteRepository
        }
    }

}
