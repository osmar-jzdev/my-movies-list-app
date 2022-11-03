package diplomado.ejercicios.osmar.mymovieslistapp.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import diplomado.ejercicios.osmar.mymovieslistapp.R
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieEntity
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieViewModel
import diplomado.ejercicios.osmar.mymovieslistapp.databinding.ActivityInsertBinding

class InsertActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding : ActivityInsertBinding
    private var genreIndexFlag: Boolean = false
    private var genreSelected: String = ""
    private var imgGenreResourceId = 0
    private var genreSpinnerIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

    }



    fun click(view: View) {
        var movieViewModel: MovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        with(binding){
            when{
                txtInputEditTxtTitle.text.toString().isEmpty() -> {
                    txtInputEditTxtTitle.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(this@InsertActivity, R.string.pleaseFillEverything, Toast.LENGTH_SHORT).show()
                }
                txtInputEditTxtPlatform.text.toString().isEmpty() -> {
                    txtInputEditTxtPlatform.error = resources.getString(R.string.errorEmptyMsg)
                    Toast.makeText(this@InsertActivity, R.string.pleaseFillEverything, Toast.LENGTH_SHORT).show()
                }
                !genreIndexFlag -> {
                    Toast.makeText(this@InsertActivity, R.string.pleaseSelectGenre, Toast.LENGTH_SHORT).show()
                }
                ratingBarScore.rating.toInt() == 0 ->{
                    Toast.makeText(this@InsertActivity, R.string.pleaseRate, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val movie = MovieEntity(0,
                                            txtInputEditTxtTitle.text.toString(),
                                            txtInputEditTxtPlatform.text.toString(),
                                            genreSelected,
                                            ratingBarScore.rating.toInt(),
                                            imgGenreResourceId,
                                            genreSpinnerIndex)
                    movieViewModel.addMovie(movie)
                    if(movie.id == 0){
                        Toast.makeText(this@InsertActivity, R.string.movieSavedSuccess, Toast.LENGTH_SHORT).show()
                        txtInputEditTxtTitle.setText("")
                        txtInputEditTxtPlatform.setText("")
                        genreSpinner.setSelection(0)
                        txtInputEditTxtTitle.requestFocus()
                        ratingBarScore.rating = 0F
                    }else{
                        Toast.makeText(this@InsertActivity, R.string.errorMovieSaved, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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