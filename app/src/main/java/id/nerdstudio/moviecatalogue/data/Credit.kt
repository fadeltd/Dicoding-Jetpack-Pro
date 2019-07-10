package id.nerdstudio.moviecatalogue.data

abstract class Credit {
    abstract val id: Long
    abstract val creditId: String?
    abstract val name: String
    abstract val gender: Int
    abstract val profilePath: String?
}
