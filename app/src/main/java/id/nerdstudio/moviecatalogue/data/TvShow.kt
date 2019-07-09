package id.nerdstudio.moviecatalogue.data

import com.google.gson.annotations.SerializedName
import java.util.Arrays

data class TvShow(
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("genre_ids")
    override val genreIds: IntArray = intArrayOf(),
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("popularity")
    override val popularity: Float = 0F,
    @SerializedName("origin_country")
    val originCountry: Array<String> = emptyArray(),
    @SerializedName("vote_count")
    override val voteCount: Long = 0,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("backdrop_path")
    override val backdropPath: String,
    @SerializedName("original_language")
    override val originalLanguage: String? = null,
    @SerializedName("id")
    override val id: Long = 0L,
    @SerializedName("vote_average")
    override val voteAverage: Float = 0F,
    @SerializedName("overview")
    override val overview: String? = null,
    @SerializedName("poster_path")
    override val posterPath: String? = null,
    @SerializedName("created_by")
    val createdBy: Array<Credit> = emptyArray(),
    @SerializedName("episode_run_time")
    val episodeRunTime: Int = 0,
    @SerializedName("genres")
    override val genres: Array<Genre> = emptyArray(),
    @SerializedName("homepage")
    override val homepage: String? = null,
    @SerializedName("in_production")
    val inProduction: Boolean = false,
    @SerializedName("languages")
    val languages: Array<String> = emptyArray(),
    @SerializedName("last_air_date")
    val lastAirDate: String? = null,
    @SerializedName(" last_episode_to_air")
    val lastEpisodeToAir: Episode? = null,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: Episode? = null,
    @SerializedName("networks")
    val network: Array<ProductionCompany> = emptyArray(),
    @SerializedName("number_of_episodes")
    val numberOfEpisode: Int = 0,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int = 0,
    @SerializedName("production_companies")
    override val productionCompanies: Array<ProductionCompany> = emptyArray(),
    @SerializedName("seasons")
    val seasons: Array<Season> = emptyArray(),
    @SerializedName("status")
    override val status: String? = null,
    @SerializedName("type")
    val type: String? = null
) : Show(
    id,
    voteAverage,
    popularity,
    posterPath,
    originalLanguage,
    genreIds,
    backdropPath,
    overview,
    genres,
    homepage,
    productionCompanies,
    status,
    voteCount
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        other as TvShow
        if (!Arrays.equals(genreIds, other.genreIds) &&
            !Arrays.equals(originCountry, other.originCountry)
        )
            return false
        return true
    }

    override fun hashCode(): Int {
        return super.hashCode() + Arrays.hashCode(genreIds) + Arrays.hashCode(originCountry)
    }
}