package id.nerdstudio.moviecatalogue.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Crew(
    @SerializedName("credit_id")
    override val creditId: String?,
    @SerializedName("department")
    val department: String? = null,
    @SerializedName("id")
    override val id: Long,
    @SerializedName("name")
    override val name: String,
    @SerializedName("gender")
    override val gender: Int,
    @SerializedName("job")
    val job: String? = null,
    @SerializedName("profile_path")
    override val profilePath: String?
) : Credit(), Parcelable