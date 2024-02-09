package net.iessochoa.radwaneabdessamie.practica6.ui.favoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje
import net.iessochoa.radwaneabdessamie.practica6.repository.Repository

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repositorio: Repository
    val personajesLiveData:LiveData<List<Personaje>>

    init {
        //iniciamos el repositorio
        Repository(getApplication<Application>().applicationContext)
        repositorio=Repository
        //observaremos los cambios en la lista de personajes
        personajesLiveData= repositorio.getPersonajesFavoritos()
        //carga la primera página de personajes

    }

    fun delPersonaje(personaje: Personaje) = viewModelScope.launch(Dispatchers.IO){
        Repository.delPersonaje(personaje)
    }

}