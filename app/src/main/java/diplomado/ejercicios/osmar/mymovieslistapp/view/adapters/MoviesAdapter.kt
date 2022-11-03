package diplomado.ejercicios.osmar.mymovieslistapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import diplomado.ejercicios.osmar.mymovieslistapp.data.MovieEntity
import diplomado.ejercicios.osmar.mymovieslistapp.databinding.ListElementBinding
import diplomado.ejercicios.osmar.mymovieslistapp.view.activities.MainActivity

class MoviesAdapter(private val context: Context): RecyclerView.Adapter<MoviesAdapter.ViewHolder>(){

    private var movies = emptyList<MovieEntity>()
    private val layoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: ListElementBinding): RecyclerView.ViewHolder(view.root){
        val txtViewTitle = view.txtViewTitle
        val txtViewGenre = view.txtViewGenre
        val txtViewScore = view.txtViewScore
        val imgViewGenre = view.imgViewGenre
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListElementBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtViewTitle.text = movies[position].title
        holder.txtViewGenre.text = movies[position].genre
        holder.txtViewScore.text = movies[position].score.toString()
        holder.imgViewGenre.setImageResource(movies[position].imgResourceId)

        //view holder is used for the click of each elements
        holder.itemView.setOnClickListener {
            //Here I manage the click from a list element selected
            if(context is MainActivity) context.selectedMovie(movies[position])
        }

    }

    fun setUpData(movies: List<MovieEntity>){
        this.movies =  movies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}