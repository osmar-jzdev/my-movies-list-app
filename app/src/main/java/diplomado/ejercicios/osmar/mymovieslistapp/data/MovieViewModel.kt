package diplomado.ejercicios.osmar.mymovieslistapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): AndroidViewModel(application) {

    val repository: MovieRepository
    val readAllData: LiveData<List<MovieEntity>>

    init {
        val movieDao = DBMovies.getDatabase(application).movieDao()
        repository = MovieRepository(movieDao)
        readAllData = repository.readAllData.asLiveData()
    }

    fun addMovie(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovieData(movie)
        }
    }

    fun updateMovie(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMovie(movie)
        }
    }


    fun deleteMovie(movie: MovieEntity){
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }
}