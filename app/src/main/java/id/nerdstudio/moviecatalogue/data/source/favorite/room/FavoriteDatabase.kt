package id.nerdstudio.moviecatalogue.data.source.favorite.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters
import id.nerdstudio.moviecatalogue.data.entity.Converters
import id.nerdstudio.moviecatalogue.data.entity.Movie
import id.nerdstudio.moviecatalogue.data.entity.TvShow

@Database(entities = [Movie::class, TvShow::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        private const val dbName = "MovieCatalogue.db"
        private lateinit var INSTANCE: FavoriteDatabase
        fun getInstance(context: Context): FavoriteDatabase {
            synchronized(FavoriteDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteDatabase::class.java, dbName
                    ).build()
                }
                return INSTANCE
            }
        }
    }
}