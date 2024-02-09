package net.iessochoa.radwaneabdessamie.practica6.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje

@Dao
interface PersonajeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPersonaje(personaje: Personaje)

    @Delete
    suspend fun delPersonaje(personaje: Personaje)

    @Query("SELECT * FROM personajes")
    fun getAllPersonajesFavoritos(): LiveData<List<Personaje>>
}