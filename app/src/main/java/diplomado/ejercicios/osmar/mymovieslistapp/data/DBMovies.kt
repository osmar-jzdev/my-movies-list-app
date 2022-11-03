package diplomado.ejercicios.osmar.mymovieslistapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import diplomado.ejercicios.osmar.mymovieslistapp.utils.Constants

@Database(entities = [MovieEntity::class], version = 1)
abstract class DBMovies: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile //lo que se escriba a este campo, será inmediatamente visible a otros hilos (threads)
        private var INSTANCE: DBMovies? = null

        fun getDatabase(context: Context): DBMovies {
            //Si la instancia no es nula, entonces se regresa
            //Si es nula, se crea la base de datos (patrón Singleton)
            return INSTANCE ?: synchronized(this) {  //usando el operador elvis
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DBMovies::class.java,
                    Constants.DATABASE_NAME
                ).fallbackToDestructiveMigration() //permite a Room recrear las tablas de la BD si las migraciones para migrar el esquema al más reciente no son encontradas
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}