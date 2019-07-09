package id.nerdstudio.moviecatalogue.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.Arrays

//data class Movie(
//    override val id: Long = 0L,
//    @SerializedName("video")
//    val video: Boolean = false,
//    override val voteAverage: Float = 0F,
//    @SerializedName("title")
//    val title: String? = null,
//    override val popularity: Float = 0F,
//    override val posterPath: String? = null,
//    override val originalLanguage: String? = null,
//    @SerializedName("original_title")
//    val originalTitle: String? = null,
//    override val genreIds: IntArray = intArrayOf(),
//    override val backdropPath: String? = null,
//    @SerializedName("adult")
//    val adult: Boolean = false,
//    override val overview: String? = null,
//    @SerializedName("release_date")
//    val releaseDate: String? = null,
//    @SerializedName("budget")
//    val budget: Long = 0L,
//    override val genres: Array<Genre> = emptyArray(),
//    override val homepage: String? = null,
//    @SerializedName("imdb_id")
//    val imdbId: String? = null,
//    override val productionCompanies: Array<ProductionCompany> = emptyArray(),
//    @SerializedName("production_countries")
//    val productionCountry: Array<ProductionCountry> = emptyArray(),
//    @SerializedName("revenue")
//    val revenue: Long? = null,
//    @SerializedName("runtime")
//    val runtime: Int? = null,
//    @SerializedName("spoken_languages")
//    val spokenLanguages: Array<SpokenLanguage> = emptyArray(),
//    @SerializedName("status")
//    override val status: String? = null,
//    @SerializedName("tagline")
//    val tagline: String? = null,
//    override val voteCount: Long = 0L
//) : Show(
//    id,
//    voteAverage,
//    popularity,
//    posterPath,
//    originalLanguage,
//    genreIds,
//    backdropPath,
//    overview,
//    genres,
//    homepage,
//    productionCompanies,
//    status,
//    voteCount
//) { }
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
        if (!Arrays.equals(genreIds, other.genreIds))
            return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode() + Arrays.hashCode(genreIds)
    }
}