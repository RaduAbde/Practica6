package net.iessochoa.radwaneabdessamie.practica6.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date
@Entity(tableName = "personajes")
@Parcelize
data class Personaje(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val created: Date,



    /*
    val episode: List<String>,
    val location: Location,

    val origin: Origin,
    val url: String*/
): Parcelable


