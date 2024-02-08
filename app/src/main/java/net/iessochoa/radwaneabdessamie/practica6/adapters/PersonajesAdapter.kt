package net.iessochoa.radwaneabdessamie.practica6.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.radwaneabdessamie.practica6.databinding.ItemPersonajeBinding
import net.iessochoa.radwaneabdessamie.practica6.model.Personaje
import net.iessochoa.radwaneabdessamie.practica6.utils.cargaImagen

class PersonajesAdapter :RecyclerView.Adapter<PersonajesAdapter.PersonajeViewHolder>(){

    var listaPersonajes: List<Personaje>?=null
    var onPersonajeClickListener:OnPersonajeClickListener?=null
    fun setLista(lista:List<Personaje>){
        listaPersonajes=lista
        //notifica al adaptador que hay cambios y tiene que redibujar el ReciclerView
        notifyDataSetChanged()
    }
    inner class PersonajeViewHolder(val binding: ItemPersonajeBinding)
        : RecyclerView.ViewHolder(binding.root){
            init {
                //inicio del click sobre el Layout
                binding.root.setOnClickListener(){
                    val personaje = listaPersonajes?.get(this.adapterPosition)
                    onPersonajeClickListener?.onPersonajeClik(personaje)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PersonajeViewHolder {
        //utilizamos binding, en otro caso hay que indicar el item.xml.
        //Para más detalles puedes verlo en la documentación
        val binding = ItemPersonajeBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonajeViewHolder(binding)
    }


    //tamaño de la lista
    override fun getItemCount(): Int = listaPersonajes?.size?:0

    override fun onBindViewHolder(personajeViewHolder: PersonajeViewHolder, pos:
    Int) {
        //Nos pasan la posición del item a mostrar en el viewHolder
        with(personajeViewHolder) {
            //cogemos la tarea a mostrar y rellenamos los campos del ViewHolder
            with(listaPersonajes!!.get(pos)) {
                binding.tvGenero.text = gender
                binding.tvNombre.text = name
                cargaImagen(binding.ivFoto, image)
            }
        }
    }

    interface OnPersonajeClickListener{
        fun onPersonajeClik(personaje: Personaje?)
    }


}