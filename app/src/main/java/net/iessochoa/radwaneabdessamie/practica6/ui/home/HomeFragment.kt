package net.iessochoa.radwaneabdessamie.practica6.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.radwaneabdessamie.practica6.adapters.PersonajesAdapter
import net.iessochoa.radwaneabdessamie.practica6.databinding.FragmentHomeBinding
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by activityViewModels()
    lateinit var personajesAdapter: PersonajesAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome
        /*homeViewModel.personajesLiveData
            .observe(viewLifecycleOwner,Observer<List<Personaje>>) {
                val text=StringBuilder()
                actualizaLista()
                //it es la lista de personajes actualizada
                for(personaje in it)

                    text.append(personaje.name).append("\n")
                //textView.text = text
            }*/

        homeViewModel.personajesLiveData
            .observe(viewLifecycleOwner,Observer<List<Personaje>> { lista ->
                personajesAdapter.setLista(lista)
            })

        return root
    }

    private fun defineDetectarFinRecycler() {
        val scrollListener=object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int)
            {
                super.onScrollStateChanged(recyclerView, newState)
                //si llegamos al final
                if (recyclerView.canScrollVertically(-1) &&
                    !recyclerView.canScrollVertically(1)){
                    //recuperamos los 20 siguientes, eso hará que se active el observador
                    homeViewModel.getNextPersonajes();
                }
            }
        }
        //añadimos el evento al RecyclerView
        binding.rvTareas.addOnScrollListener(scrollListener)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       iniciaRecyclerView()
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
        defineDetectarFinRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}