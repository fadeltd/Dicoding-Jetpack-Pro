package id.nerdstudio.moviecatalogue.data

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id") val id: Long? = 0,
    @SerializedName("vote_average") val voteAverage: Float = 0F,
    @SerializedName("title") val title: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null
)