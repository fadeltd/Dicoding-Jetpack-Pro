package id.nerdstudio.moviecatalogue.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductionCompany(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("logo_path")
    val logoPath: String? = null,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("origin_country")
    val originCountry: String = ""
) : Parcelable
