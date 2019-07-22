package id.nerdstudio.moviecatalogue.data.entity

abstract class Catalogue {
    abstract val id: Long
    abstract val voteAverage: Float
    abstract val popularity: Float
    abstract val posterPath: String?
    abstract val originalLanguage: String?
    abstract val genreIds: IntArray
    abstract val backdropPath: String?
    abstract val overview: String?
    abstract val genres: Array<Genre>
    abstract val homepage: String?
    abstract val productionCompanies: Array<ProductionCompany>
    abstract val status: String?
    abstract val voteCount: Long
}