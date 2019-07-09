package id.nerdstudio.moviecatalogue.data

import com.google.gson.annotations.SerializedName
import id.nerdstudio.moviecatalogue.data.Credit

data class Crew(
    override val creditId: String?,
    @SerializedName("department")
    val department: String? = null,
    override val id: Long,
    override val name: String,
    override val gender: Int,
    @SerializedName("job")
    val job: String? = null,
    override val profilePath: String?
) : Credit(id, creditId, name, gender, profilePath)