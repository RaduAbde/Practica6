package net.iessochoa.radwaneabdessamie.practica6.ui

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.iessochoa.radwaneabdessamie.practica6.R
import net.iessochoa.radwaneabdessamie.practica6.databinding.FragmentPersonajeBinding
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje
import net.iessochoa.radwaneabdessamie.practica6.ui.home.HomeViewModel
import net.iessochoa.radwaneabdessamie.practica6.utils.cargaImagen
import java.text.DateFormat
import java.util.Locale


class PersonajeFragment : Fragment() {

    private var _binding:FragmentPersonajeBinding? = null
    val args: PersonajeFragmentArgs by navArgs()
    //private val viewModel: HomeViewModel by activityViewModels()
    private val binding get() = _binding!!



    private fun iniciaDatos(){
        var personaje: Personaje = args.personaje;
        binding.tvNombre.text=personaje.name
        cargaImagen(binding.ivFotoDetail,personaje.image)
        binding.tvEspecie.text=personaje.species
        binding.tvFecha.text= DateFormat
            .getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
            .format(personaje.created)
        binding.tvEstado2.text=personaje.status
        binding.tvGeneroDetail.text=personaje.gender

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciaDatos()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonajeBinding.inflate(inflater,container,false)
        return  binding.root
    //return inflater.inflate(R.layout.fragment_personaje, container, false)
    }


}