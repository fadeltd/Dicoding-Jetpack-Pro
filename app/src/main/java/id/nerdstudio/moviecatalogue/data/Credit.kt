package id.nerdstudio.moviecatalogue.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Credit(
    @SerializedName("id")
    open val id: Long = 0L,
    @SerializedName("credit_id")
    open val creditId: String? = null,
    @SerializedName("name")
    open val name: String = "",
    @SerializedName("gender")
    open val gender: Int = 0,
    @SerializedName("profilePath")
    open val profilePath: String? = null
) : Parcelable
