package id.nerdstudio.moviecatalogue.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.Arrays

@Parcelize
data class Movie(
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("video")
    val video: Boolean = false,
    @SerializedName("vote_average")
    val voteAverage: Float = 0F,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("popularity")
    val popularity: Float = 0F,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("genre_ids")
    val genreIds: IntArray = intArrayOf(),
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("adult")
    val adult: Boolean = false,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("budget")
    val budget: Long = 0L,
    @SerializedName("genres")
    val genres: Array<Genre> = emptyArray(),
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("production_companies")
    val productionCompanies: Array<ProductionCompany> = emptyArray(),
    @SerializedName("production_countries")
    val productionCountry: Array<ProductionCountry> = emptyArray(),
    @SerializedName("revenue")
    val revenue: Long? = null,
    @SerializedName("runtime")
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: Array<SpokenLanguage> = emptyArray(),
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("tagline")
    val tagline: String? = null,
    @SerializedName("vote_count")
    val voteCount: Long = 0L
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as Movie
        if (!Arrays.equals(genreIds, other.genreIds) &&
            !Arrays.equals(genres, other.genres) &&
            !Arrays.equals(productionCompanies, other.productionCompanies) &&
            !Arrays.equals(productionCountry, other.productionCountry) &&
            !Arrays.equals(spokenLanguages, other.spokenLanguages)
        )
            return false
        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(genreIds) +
                Arrays.hashCode(genres) +
                Arrays.hashCode(productionCompanies) +
                Arrays.hashCode(productionCountry) +
                Arrays.hashCode(spokenLanguages)
    }
}