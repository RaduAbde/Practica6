package net.iessochoa.radwaneabdessamie.practica6.repository

import android.app.Application
import android.content.Context
import net.iessochoa.radwaneabdessamie.practica6.db.PersonajeDao
import net.iessochoa.radwaneabdessamie.practica6.db.PersonajeDataBase
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje
import net.iessochoa.radwaneabdessamie.practica6.network.NetworkService

object Repository {
    //necesitaremos el context en las capas inferiores
    private lateinit var application: Application
    private lateinit var modelPersonaje:PersonajeDao
    //iniciamos la instancia
    operator fun invoke(context: Context) {
        this.application = context.applicationContext as Application
        NetworkService(application)
        modelPersonaje=PersonajeDataBase.getDatabase(application).personajeDao()
    }

    fun getEstadoServicioLiveData()=NetworkService.getEstadoServicioLiveData()
    suspend fun getNextPersonajes()=NetworkService.getNextPersonajes()

    suspend fun delPersonaje(personaje: Personaje)= modelPersonaje.delPersonaje(personaje)
    fun getLiveDataListaPersonajes ()=NetworkService.getLiveDataListaPersonajes()

    fun getPersonajesFavoritos()= modelPersonaje.getAllPersonajesFavoritos()
    suspend fun addPersonaje(personaje: Personaje)= modelPersonaje.addPersonaje(personaje)
}