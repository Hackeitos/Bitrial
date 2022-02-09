package com.bitrial.bitrial

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

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