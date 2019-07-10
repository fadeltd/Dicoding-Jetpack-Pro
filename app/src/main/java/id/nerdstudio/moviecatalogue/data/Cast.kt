package id.nerdstudio.moviecatalogue.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cast(
    @SerializedName("character")
    val character: String = "",
    @SerializedName("credit_id")
    override val creditId: String? = null,
    @SerializedName("id")
    override val id: Long,
    @SerializedName("name")
    override val name: String,
    @SerializedName("gender")
    override val gender: Int,
    @SerializedName("profile_path")
    override val profilePath: String?,
    @SerializedName("order")
    val order: Int = 0
) : Credit(), Parcelable