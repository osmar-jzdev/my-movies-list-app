package diplomado.ejercicios.osmar.mymovieslistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplomado.ejercicios.osmar.mymovieslistapp.utils.Constants

@Entity(tableName = Constants.DATABASE_MOVIES_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val platform: String,
    val genre: String,
    val score: Int,
    val imgResourceId: Int,
    val genreSpinnerIndex: Int
)