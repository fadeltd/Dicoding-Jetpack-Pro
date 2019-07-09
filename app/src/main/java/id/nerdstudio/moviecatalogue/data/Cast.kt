package id.nerdstudio.moviecatalogue.data

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("character")
    val character: String = "",
    override val creditId: String?,
    override val id: Long,
    override val name: String,
    override val gender: Int,
    override val profilePath: String?,
    @SerializedName("character")
    val order: Int = 0
) : Credit(id, creditId, name, gender, profilePath)