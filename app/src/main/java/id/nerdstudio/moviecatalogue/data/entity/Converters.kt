package id.nerdstudio.moviecatalogue.data.entity

import androidx.room.TypeConverter
import id.nerdstudio.moviecatalogue.util.fromJson
import id.nerdstudio.moviecatalogue.util.toJson

class Converters {
    companion object {
        @JvmStatic
        @TypeConverter
        fun toGenre(value: String): Array<Genre> = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun toProductionCompany(value: String): Array<ProductionCompany> = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun toProductionCountry(value: String): Array<ProductionCountry> = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun toSpokenLanguage(value: String): Array<SpokenLanguage> = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun toArrayString(value: String): Array<String> = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun toIntArray(value: String): IntArray = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun toArrayCrew(value: String): Array<Crew> = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun toArraySeason(value: String): Array<Season> = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun fromObject(value: Any): String = toJson(value)
    }
}
