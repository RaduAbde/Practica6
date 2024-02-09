package net.iessochoa.radwaneabdessamie.practica6.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.radwaneabdessamie.practica6.adapters.PersonajesAdapter
import net.iessochoa.radwaneabdessamie.practica6.databinding.FragmentDashboardBinding
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje
import net.iessochoa.radwaneabdessamie.practica6.ui.home.HomeFragmentDirections

class FavoritosFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var personajesAdapter: PersonajesAdapter
    private val favouriteViewModel:FavouriteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaRecyclerView()
        favouriteViewModel.personajesLiveData.observe(viewLifecycleOwner, Observer<List<Personaje>>{lista ->
            personajesAdapter.setLista(lista)
        })
        accionDetalle()
    }

    private fun accionDetalle(){
        personajesAdapter.onPersonajeClickListener = object :
            PersonajesAdapter.OnPersonajeClickListener{
            override fun onPersonajeClik(personaje: Personaje?) {
                val action = FavoritosFragmentDirections.actionNavigationFavoritosToPersonajeFragment(personaje!!)
                findNavController().navigate(action)
            }
        }
    }

    fun iniciaSwiped(){
        //creamos el evento del Swiper para detectar cuando el usuario  desliza un item
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or
                        ItemTouchHelper.RIGHT) {
                //si tenemos que actuar cuando se mueve un item
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder,
                                      direction: Int) {

                    val posicion = viewHolder.adapterPosition
                    //obtenemos la posición de la tarea a partir del viewholder
                    val personajeAdd=personajesAdapter.listaPersonajes?.get(posicion)
                    //borramos la tarea. Falta preguntar al usuario si desea borrarla
                    if (personajeAdd != null) {
                        favouriteViewModel.delPersonaje(personajeAdd)
                    }
                    personajesAdapter.notifyItemChanged(posicion)
                }
            }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        //asignamos el evento al RecyclerView
        itemTouchHelper.attachToRecyclerView(binding.rvTareas)
    }

    private fun iniciaRecyclerView() {
        //creamos el adaptador
        personajesAdapter = PersonajesAdapter()

        with(binding.rvTareas) {
            //Creamos el layoutManager
            layoutManager = LinearLayoutManager(activity)
            //le asignamos el adaptador
            adapter = personajesAdapter
        }
        iniciaSwiped()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}