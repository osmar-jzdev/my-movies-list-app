package diplomado.ejercicios.osmar.mymovieslistapp.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import diplomado.ejercicios.osmar.mymovieslistapp.R
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieEntity
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieViewModel
import diplomado.ejercicios.osmar.mymovieslistapp.databinding.ActivityEditBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityEditBinding
    private lateinit var movieViewModel: MovieViewModel
    private var movieSelected: MovieEntity? = null
    private var genreIndexFlag: Boolean = false
    private var genreSelected: String = ""
    private var imgGenreResourceId = 0
    private var genreSpinnerIndex = 0;
    private var id = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        val bundle = intent.extras
        if(bundle!=null)
            id = bundle.getInt("ID", 0)


        val spinner: Spinner = binding.genreSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.genre_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this


        GlobalScope.launch {
            movieSelected = movieViewModel.repository.getMovie(id)
            setDataMovieSelected()
        }

    }

    private suspend fun setDataMovieSelected(){
        withContext(Dispatchers.Main) {
            //Toast.makeText(this@DetailsActivity, "movie: ${movieSelected?.title}", Toast.LENGTH_SHORT).show()
            with(binding){
                txtInputEditTxtTitle.setText(movieSelected?.title)
                txtInputEditTxtPlatform.setText(movieSelected?.platform)
                movieSelected?.genreSpinnerIndex?.let { genreSpinner.setSelection(it) }
                ratingBarScore.rating = movieSelected?.score?.toFloat() ?: 0F
            }
        }
    }

    fun click(view: View) {
        with(binding){
            when{
                txtInputEditTxtTitle.text.toString().isEmpty() -> {
                    txtInputEditTxtTitle.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(this@EditActivity, R.string.pleaseFillEverything, Toast.LENGTH_SHORT).show()
                }
                txtInputEditTxtPlatform.text.toString().isEmpty() -> {
                    txtInputEditTxtPlatform.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(this@EditActivity, R.string.pleaseFillEverything, Toast.LENGTH_SHORT).show()
                }
                !genreIndexFlag -> {
                    Toast.makeText(this@EditActivity, R.string.pleaseSelectGenre, Toast.LENGTH_SHORT).show()
                }
                ratingBarScore.rating.toInt() == 0 ->{
                    Toast.makeText(this@EditActivity, R.string.pleaseRate, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val movie = MovieEntity(id,
                        txtInputEditTxtTitle.text.toString(),
                        txtInputEditTxtPlatform.text.toString(),
                        genreSelected,
                        ratingBarScore.rating.toInt(),
                        imgGenreResourceId,
                        genreSpinnerIndex)
                    movieViewModel.updateMovie(movie)
                    if(movie.id == id){
                        Toast.makeText(this@EditActivity, R.string.movieSavedSuccess, Toast.LENGTH_SHORT).show()
                        val intent  = Intent(this@EditActivity, DetailsActivity::class.java)
                        intent.putExtra("ID", id)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@EditActivity, R.string.errorMovieSaved, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent  = Intent(this, DetailsActivity::class.java)
        intent.putExtra("ID", id)
        startActivity(intent)
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val resourceIdsGenre = listOf(R.drawable.adventure,
            R.drawable.comedy,
            R.drawable.fantasy,
            R.drawable.crime,
            R.drawable.drama,
            R.drawable.romance,
            R.drawable.sci_fi,
            R.drawable.thriller,
            R.drawable.horror,
            R.drawable.western)
        when(position) {
            in 1..10 -> {
                genreIndexFlag = true
                genreSelected = parent?.getItemAtPosition(position).toString()
                imgGenreResourceId = resourceIdsGenre.get(position - 1)
                genreSpinnerIndex = position
            }else -> {
            genreIndexFlag = false
        }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}