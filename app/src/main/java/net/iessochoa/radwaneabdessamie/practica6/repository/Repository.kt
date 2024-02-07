package net.iessochoa.radwaneabdessamie.practica6.repository

import android.app.Application
import android.content.Context
import net.iessochoa.radwaneabdessamie.practica6.network.NetworkService

object Repository {
    //necesitaremos el context en las capas inferiores
    private lateinit var application: Application
    //iniciamos la instancia
    operator fun invoke(context: Context) {
        this.application = context.applicationContext as Application
        NetworkService(application)
    }

    fun getEstadoServicioLiveData()=NetworkService.getEstadoServicioLiveData()
    suspend fun getNextPersonajes()=NetworkService.getNextPersonajes()
    fun getLiveDataListaPersonajes ()=NetworkService.getLiveDataListaPersonajes()
}