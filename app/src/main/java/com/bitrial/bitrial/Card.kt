package com.bitrial.bitrial

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class Pregunta(json: JSONObject) {
    val pregunta: String = json.getString("pregunta")
    val respuesta: String = json.getString("respuesta")
}

class Card(json: JSONObject) {
    val geografia = Pregunta(json.getJSONObject("geografia"))
    val entretenimiento = Pregunta(json.getJSONObject("entretenimiento"))
    val historia = Pregunta(json.getJSONObject("historia"))
    val literatura = Pregunta(json.getJSONObject("literatura"))
    val ciencias = Pregunta(json.getJSONObject("ciencias"))
    val deportes = Pregunta(json.getJSONObject("deportes"))

    companion object {
        private const val address = "http://10.0.2.2:5000"
//        private const val address = "http://localhost:5000"

        fun get(queue: RequestQueue): Card? {
            val request = JsonObjectRequest(Request.Method.GET, "$address/card", null,
                { response ->
                    println(response.toString(4))
                },
                { error -> println(error) })
            queue.add(request)
            return null
        }
    }
}