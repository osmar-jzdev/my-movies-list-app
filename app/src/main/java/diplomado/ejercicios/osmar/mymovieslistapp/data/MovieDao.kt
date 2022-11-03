package diplomado.ejercicios.osmar.mymovieslistapp.data

import androidx.room.*
import diplomado.ejercicios.osmar.mymovieslistapp.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM ${Constants.DATABASE_MOVIES_TABLE} ORDER BY id ASC")
    fun readData(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM ${Constants.DATABASE_MOVIES_TABLE} WHERE ID = :id")
    suspend fun getMovie(id: Int): MovieEntity

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
}