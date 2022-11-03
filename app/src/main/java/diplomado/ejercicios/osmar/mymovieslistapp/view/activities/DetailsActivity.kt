package diplomado.ejercicios.osmar.mymovieslistapp.view.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import diplomado.ejercicios.osmar.mymovieslistapp.R
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieEntity
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieViewModel
import diplomado.ejercicios.osmar.mymovieslistapp.databinding.ActivityDetailsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding
    private lateinit var movieViewModel: MovieViewModel
    private var movieSelected: MovieEntity? = null
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        val bundle = intent.extras
        if(bundle!=null) {
            id = bundle.getInt("ID", 0)
            //Toast.makeText(this, "id: $id", Toast.LENGTH_SHORT).show()
        }

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
                txtInputEditTxtGenre.setText(movieSelected?.genre)
                ratingBarScore.rating = movieSelected?.score?.toFloat() ?: 0F

                //block the elements to prevent the user type or edit from this screen
                txtInputEditTxtTitle.inputType = InputType.TYPE_NULL
                txtInputEditTxtPlatform.inputType = InputType.TYPE_NULL
                txtInputEditTxtGenre.inputType = InputType.TYPE_NULL
                ratingBarScore.setIsIndicator(true)
            }
        }
    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit -> {
                val intent =  Intent(this, EditActivity::class.java)
                intent.putExtra("ID", id)
                startActivity(intent)
                finish()
            }
            R.id.btnDelete ->{
                AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.alertTitle))
                    .setMessage(resources.getString(R.string.alertDeleteMsg, movieSelected?.title))
                    .setPositiveButton(resources.getString(R.string.alertAccept), DialogInterface.OnClickListener { dialog, which ->
                        movieSelected?.let { movieViewModel.deleteMovie(it) }
                        startActivity(Intent(this@DetailsActivity, MainActivity::class.java))
                        finish()
                    })
                    .setNegativeButton(resources.getString(R.string.alertCancel), DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    .show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}