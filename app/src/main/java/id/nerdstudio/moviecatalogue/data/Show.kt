package id.nerdstudio.moviecatalogue.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Show(
    @SerializedName("id")
    open val id: Long = 0L,
    @SerializedName("vote_average")
    open val voteAverage: Float = 0F,
    @SerializedName("popularity")
    open val popularity: Float = 0F,
    @SerializedName("poster_path")
    open val posterPath: String? = null,
    @SerializedName("original_language")
    open val originalLanguage: String? = null,
    @SerializedName("genre_ids")
    open val genreIds: IntArray = intArrayOf(),
    @SerializedName("backdrop_path")
    open val backdropPath: String? = null,
    @SerializedName("overview")
    open val overview: String? = null,
    @SerializedName("genres")
    open val genres: Array<Genre> = emptyArray(),
    @SerializedName("homepage")
    open val homepage: String? = null,
    @SerializedName("production_companies")
    open val productionCompanies: Array<ProductionCompany> = emptyArray(),
    @SerializedName("status")
    open val status: String? = null,
    @SerializedName("vote_count")
    open val voteCount: Long = 0L
) : Parcelable