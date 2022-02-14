package com.bitrial.bitrial

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

// Clase que permite interactuar con la API de preguntas de bitrial™
// Los metodos admiten los siguientes parametros:
// endpoint: El endpoint de la api (ejemplo "/card")
// [OPCIONAL] data: En caso de ser una request de tipo POST, datos que pasar al servidor
// onSuccess: Metodo al que llamar una vez se haya completado con exito
// onError: Metodo al que llamar si algo ha ido mal
object Requester {

    private lateinit var queue: RequestQueue
    private val address = "http://10.0.2.2:5000"

    fun setContext(ctx: Context) {
        queue = Volley.newRequestQueue(ctx)
    }

    fun getJson(
        endpoint: String,
        onSuccess: (JSONObject) -> Unit,
        onError: (String?) -> Unit = {}
    ) {
        val request = JsonObjectRequest(
            Request.Method.GET, "$address/$endpoint", null,
            { response ->
                val json = JSONObject(response.toString())
                if (json.getBoolean("success"))
                    onSuccess(json.getJSONObject("data"))
                else
                    onError(json.getString("msg"))
            },
            { error ->
                error.printStackTrace()
                onError(error.message)
            })
        queue.add(request)
    }

    fun postJson(
        endpoint: String,
        data: JSONObject,
        onSuccess: (JSONObject) -> Unit,
        onError: (String?) -> Unit
    ) {
        val request = JsonObjectRequest(
            Request.Method.POST, "$address/$endpoint", data,
            { response ->
                onSuccess(response)
            },
            { error ->
                error.printStackTrace()
                onError(error.message)
            })
        queue.add(request)
    }
}