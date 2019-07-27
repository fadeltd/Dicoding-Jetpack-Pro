package id.nerdstudio.moviecatalogue.api

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.Response
import com.koushikdutta.ion.builder.Builders
import com.koushikdutta.ion.builder.LoadBuilder

class ApiLoader {
    private var context: Context? = null
    private var fragment: Fragment? = null
    private var builder: LoadBuilder<Builders.Any.B>

    constructor(context: Context) {
        this.context = context
        builder = Ion.with(context)
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
        builder = Ion.with(fragment)
    }

    private fun loader(
        url: String,
        onComplete: (e: Exception?, response: Response<JsonObject>) -> Unit
    ) {
        builder
            .load(url)
            .asJsonObject()
            .withResponse()
            .setCallback { e, response ->
                onComplete(e, response)
            }
    }

    fun loadChecker(
        url: String,
        onComplete: (() -> Unit)? = null,
        onResult: ((result: JsonObject) -> Unit)? = null,
        onFailed: ((message: String) -> Unit)? = null
    ) {
        println("Loading: $url")
        loader(url) { e, response ->
            onComplete?.invoke()
            if (e != null) {
                val message = "${e.javaClass.simpleName} ${e.message}"
                onFailed?.invoke(message)
                println(message)
            } else {
                val code = response.headers?.code()
                if (code == 200) {
                    val result = response.result
                    onResult?.invoke(result)
                } else {
                    val message = "HTTP Error Code $code"
                    onFailed?.invoke(message)
                    println(message)
                }
            }
        }
    }
}
