package id.nerdstudio.moviecatalogue.data.entity

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Episode(
    @SerializedName("air_date")
    val airDate: String? = null,
    @SerializedName("episode_number")
    val episodeNumber: Int = 0,
    @SerializedName("id")
    val id: Long = 0L,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("production_code")
    val productionCode: String? = "",
    @SerializedName("season_number")
    val seasonNumber: Int = 0,
    @SerializedName("show_id")
    val showId: Long = 0,
    @SerializedName("still_path")
    val stillPath: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Float = 0F,
    @SerializedName("vote_count")
    val voteCount: Int = 0
) : Parcelable