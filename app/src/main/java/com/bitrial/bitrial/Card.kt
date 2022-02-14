package com.bitrial.bitrial

import android.graphics.Color
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

// Indice de todas las categorias
enum class Categoria(val id: String, @DrawableRes val icon: Int, @AttrRes val color: Int) {
    GEOGRAFIA("geografia", R.drawable.ic_map, R.attr.color_geografia),
    ENTRETENIMIENTO("entretenimiento", R.drawable.ic_tv, R.attr.color_entretenimiento),
    HISTORIA("historia", R.drawable.ic_book, R.attr.color_historia),
    LITERATURA("literatura", R.drawable.ic_draw, R.attr.color_literatura),
    CIENCIAS("ciencias", R.drawable.ic_science, R.attr.color_ciencias),
    DEPORTES("deportes", R.drawable.ic_soccer, R.attr.color_deportes);
}

// CLase pregunta para tener los datos ordenados
class Pregunta(json: JSONObject) {
    val pregunta = json.getString("pregunta")
    val respuesta = json.getString("respuesta")
}

// Una tarjeta que contiene un diccionario con una pregunta en cada categoria
class Card(json: JSONObject) {
    val categories: Map<Categoria, Pregunta> = Categoria.values().map {
        it to Pregunta(json.getJSONObject(it.id))
    }.toMap()

    companion object {
        fun get(
            onSuccess: (Card) -> Unit,
            onError: (String?) -> Unit = {}
        ) {
            Requester.getJson("/card", { json -> onSuccess(Card(json)) }, onError)
        }
    }
}