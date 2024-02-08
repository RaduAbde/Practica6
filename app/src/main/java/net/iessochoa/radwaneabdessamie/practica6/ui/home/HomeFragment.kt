package net.iessochoa.radwaneabdessamie.practica6.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.radwaneabdessamie.practica6.adapters.PersonajesAdapter
import net.iessochoa.radwaneabdessamie.practica6.databinding.FragmentHomeBinding
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje
import net.iessochoa.radwaneabdessamie.practica6.network.NetworkService

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

/*
        homeViewModel.personajesLiveData
            .observe(viewLifecycleOwner,Observer<List<Personaje>> { lista ->
                personajesAdapter.setLista(lista)
            })*/

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


       iniciaBarraProgreso()
        iniciaRecyclerView()
        homeViewModel.personajesLiveData
            .observe(viewLifecycleOwner,Observer<List<Personaje>> { lista ->
                personajesAdapter.setLista(lista)
            })
        accionDetalle()
    }



    private fun iniciaBarraProgreso(){
        homeViewModel.estadoServicioLiveData
            .observe(viewLifecycleOwner){
                binding.pbLeyendoPersonajes.visibility= when(it){
                    NetworkService.EstadoServicio.LEYENDO ->View.VISIBLE
                    NetworkService.EstadoServicio.PARADO ->View.INVISIBLE
                    NetworkService.EstadoServicio.ERROR ->{
                        Toast.makeText(requireContext(),"Problemas para acceder al servivio",Toast.LENGTH_SHORT).show()
                                View.INVISIBLE
                    }
                }
                if(it== NetworkService.EstadoServicio.LEYENDO){
                    binding.pbLeyendoPersonajes.visibility=View.VISIBLE
                }
            }
    }

    private fun accionDetalle(){
        personajesAdapter.onPersonajeClickListener = object :
        PersonajesAdapter.OnPersonajeClickListener{
            override fun onPersonajeClik(personaje: Personaje?) {
                val action = HomeFragmentDirections.actionNavigationHomeToPersonajeFragment(personaje!!)
                findNavController().navigate(action)
            }
        }
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