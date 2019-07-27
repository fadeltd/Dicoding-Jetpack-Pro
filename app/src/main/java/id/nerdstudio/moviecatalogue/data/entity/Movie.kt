package id.nerdstudio.moviecatalogue.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "favorite_movies")
@Parcelize
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    override val id: Long = 0L,
    @SerializedName("video")
    val video: Boolean = false,
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    override val voteAverage: Float = 0F,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("popularity")
    override val popularity: Float = 0F,
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    override val posterPath: String? = null,
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    override val originalLanguage: String? = "en",
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("genre_ids")
    override val genreIds: IntArray = intArrayOf(),
    @SerializedName("backdrop_path")
    override val backdropPath: String? = null,
    @SerializedName("adult")
    val adult: Boolean = false,
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    override val overview: String? = null,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "budget")
    @SerializedName("budget")
    val budget: Long = 0L,
    @ColumnInfo(name = "genres")
    @SerializedName("genres")
    override val genres: Array<Genre> = emptyArray(),
    @SerializedName("homepage")
    override val homepage: String? = null,
    @SerializedName("imdb_id")
    val imdbId: String? = null,
    @SerializedName("production_companies")
    override val productionCompanies: Array<ProductionCompany> = emptyArray(),
    @SerializedName("production_countries")
    val productionCountry: Array<ProductionCountry> = emptyArray(),
    @ColumnInfo(name = "revenue")
    @SerializedName("revenue")
    val revenue: Long = 0L,
    @ColumnInfo(name = "runtime")
    @SerializedName("runtime")
    val runtime: Int? = null,
    @SerializedName("spoken_languages")
    val spokenLanguages: Array<SpokenLanguage> = emptyArray(),
    @SerializedName("status")
    override val status: String? = null,
    @SerializedName("tagline")
    val tagline: String? = null,
    @SerializedName("vote_count")
    override val voteCount: Long = 0L
) : Catalogue(), Parcelable {
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