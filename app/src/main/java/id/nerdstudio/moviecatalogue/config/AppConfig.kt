package id.nerdstudio.moviecatalogue.config

import id.nerdstudio.moviecatalogue.BuildConfig


object AppConfig {
    private const val API_KEY = BuildConfig.API_KEY
    private const val BASE_URL = BuildConfig.BASE_URL

    private const val BASE_WEB_URL = "https://www.themoviedb.org/movie/"
    private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/"
    private const val SEARCH_MOVIE = "${BASE_URL}search/movie?api_key=$API_KEY&query="

    enum class CurrentMovieType {
        NOW_PLAYING, UPCOMING
    }

    enum class Language {
        EN, ID
    }

    enum class PosterType {
        W92, W154, W185, W342, W500, W780, ORIGINAL
    }

    fun getCurrentMovies(type: CurrentMovieType = CurrentMovieType.NOW_PLAYING, language: Language = Language.EN) =
        "${BASE_URL}movie/${type.name.toLowerCase()}?api_key=$API_KEY&language=${language.name.toLowerCase()}"

    fun withQuery(query: String, language: Language) =
        "${SEARCH_MOVIE}query=$query&language=${language.name.toLowerCase()}"

    private fun getPosterPath(type: PosterType = PosterType.ORIGINAL, path: String) =
        "$BASE_IMAGE_URL${type.name.toLowerCase()}/$path"
}
