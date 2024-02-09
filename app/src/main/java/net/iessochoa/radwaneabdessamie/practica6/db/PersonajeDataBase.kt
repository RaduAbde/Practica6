package net.iessochoa.radwaneabdessamie.practica6.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje

@Database(entities = arrayOf(Personaje::class), version = 1, exportSchema
= false)
@TypeConverters(TransformaFechaSQLite::class)
public abstract class PersonajeDataBase : RoomDatabase() {
    abstract fun personajeDao(): PersonajeDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PersonajeDataBase? = null
        fun getDatabase(context: Context): PersonajeDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonajeDataBase::class.java,
                    "personaje_database"
                )
                    .build()
                INSTANCE = instance
// return instance
                instance
            }
        }
    }
}