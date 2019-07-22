package id.nerdstudio.moviecatalogue.config

import id.nerdstudio.moviecatalogue.BuildConfig
import id.nerdstudio.moviecatalogue.data.entity.Type

object AppConfig {
    private const val API_KEY = "api_key=${BuildConfig.API_KEY}"
    private const val BASE_URL = BuildConfig.BASE_URL

    private const val BASE_WEB_URL = "https://www.themoviedb.org/movie/"
    private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/"
    private const val SEARCH_MOVIE = "${BASE_URL}search/movie?api_key=$API_KEY&query="

    enum class MovieType {
        TOP_RATED, UPCOMING, NOW_PLAYING, POPULAR
    }

    enum class TvType {
        AIRING_TODAY, ON_THE_AIR, POPULAR, TOP_RATED
    }

    enum class Language {
        EN, ID
    }

    enum class PosterType {
        W92, W154, W185, W342, W500, W780, ORIGINAL
    }

    fun getMovies(type: MovieType = MovieType.NOW_PLAYING, language: Language = Language.EN) =
        "${BASE_URL}movie/${type.name.toLowerCase()}?$API_KEY&language=${language.name.toLowerCase()}"

    fun getTvShows(type: TvType = TvType.ON_THE_AIR, language: Language = Language.EN) =
        "${BASE_URL}tv/${type.name.toLowerCase()}?$API_KEY&language=${language.name.toLowerCase()}"

    fun getDetail(type: Type = Type.MOVIE, id: Long, language: Language = Language.EN) =
        "$BASE_URL${type.value}/$id?$API_KEY&language=${language.name.toLowerCase()}"

    fun getCredits(type: Type = Type.MOVIE, id: Long, language: Language = Language.EN) =
        "$BASE_URL${type.value}/$id/credits?$API_KEY&language=${language.name.toLowerCase()}"

    fun getSimilar(type: Type = Type.MOVIE, id: Long, language: Language = Language.EN) =
        "$BASE_URL${type.value}/$id/similar?$API_KEY&language=${language.name.toLowerCase()}"

    fun withQuery(query: String, language: Language) =
        "${SEARCH_MOVIE}query=$query&language=${language.name.toLowerCase()}"

    fun getImageUrl(path: String, type: PosterType = PosterType.ORIGINAL) =
        "$BASE_IMAGE_URL${type.name.toLowerCase()}$path"
}
