package id.nerdstudio.moviecatalogue.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.Arrays

@Entity(tableName = "favorite_movies")
@Parcelize
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("video")
    val video: Boolean = false,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    val voteAverage: Float = 0F,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("popularity")
    val popularity: Float = 0F,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "original_language")
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
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    val overview: String? = null,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "budget")
    @SerializedName("budget")
    val budget: Long = 0L,
    @ColumnInfo(name = "genres")
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
    @ColumnInfo(name = "revenue")
    @SerializedName("revenue")
    val revenue: Long? = null,
    @ColumnInfo(name = "runtime")
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
        ) {
            return false
        }
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