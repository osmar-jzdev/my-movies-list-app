package diplomado.ejercicios.osmar.mymovieslistapp.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    val readAllData: Flow<List<MovieEntity>> =  movieDao.readData()

    @WorkerThread
    suspend fun insertMovieData(movie: MovieEntity){
        movieDao.insertMovie(movie)
    }

    @WorkerThread
    suspend fun getMovie(id: Int): MovieEntity {
        return movieDao.getMovie(id)
    }

    @WorkerThread
    suspend fun updateMovie(movie: MovieEntity) {
        movieDao.updateMovie(movie)
    }

    @WorkerThread
    suspend fun deleteMovie(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }
}