package id.nerdstudio.moviecatalogue.data

import com.google.gson.annotations.SerializedName

data class Item(
//    @SerializedName("vote_count") val voteCount: Long? = 0,
    @SerializedName("id") val id: Long? = 0,
//    @SerializedName("video") val video: Boolean = false,
    @SerializedName("vote_average") val voteAverage: Float = 0F,
    @SerializedName("title") val title: String? = null,
//    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("poster_path") val posterPath: String? = null,
//    @SerializedName("original_language") val originalLanguage: String? = null,
//    @SerializedName("original_title") val originalTitle: String? = null,
//    @SerializedName("genre_ids") val genreIds: List<Int> = intArrayOf().toList(),
//    @SerializedName("backdrop_path") val backdropPath: String? = null,
//    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("overview") val overview: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null
)