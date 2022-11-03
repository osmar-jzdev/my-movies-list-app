package diplomado.ejercicios.osmar.mymovieslistapp.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieEntity
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieViewModel
import diplomado.ejercicios.osmar.mymovieslistapp.databinding.ActivityMainBinding
import diplomado.ejercicios.osmar.mymovieslistapp.view.adapters.MoviesAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //The following lines are to recover and show the games saved by the user in the recycler view
        val moviesAdapter =  MoviesAdapter(this)
        binding.recMovies.layoutManager = LinearLayoutManager(this)
        binding.recMovies.adapter = moviesAdapter

        val movieViewModel: MovieViewModel =
            ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.readAllData.observe(this, Observer { movie ->
            moviesAdapter.setUpData(movie)
            if(moviesAdapter.getItemCount() == 0) binding.txtViewEmptyData.visibility = View.VISIBLE
            else binding.txtViewEmptyData.visibility = View.INVISIBLE
        })
    }

    fun click(view: View) {
        startActivity(Intent(this, InsertActivity::class.java))
        finish()
    }

    fun selectedMovie(movie: MovieEntity){
        //Manage the click from the recycler view
        val intent =  Intent(this,DetailsActivity::class.java)
        intent.putExtra("ID", movie.id)
        startActivity(intent)
        finish() //destroy because when we back to the main screen the app needs to refresh the data*/
    }
}