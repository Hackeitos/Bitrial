package com.bitrial.bitrial

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bitrial.bitrial.databinding.FragmentSubmitQuestionBinding
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

// Fragment para poder proponer preguntas a la base de datos
class SubmitQuestionFragment : Fragment() {

    lateinit var binding: FragmentSubmitQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubmitQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val pregunta = binding.editTextPregunta.text.trim()
            val respuesta = binding.editTextRespuesta.text.trim()

            if (pregunta.isBlank()) {
                Snackbar.make(
                    binding.root,
                    "La pregunta no puede estar vacia",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (respuesta.isBlank()) {
                Snackbar.make(
                    binding.root,
                    "La respuesta no puede estar vacia",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val jsonReq = JSONObject()
            jsonReq.put("question", pregunta)
            jsonReq.put("answer", respuesta)

            val showSuccessDialog = {
                AlertDialog.Builder(requireContext()).setTitle("Pregunta añadida")
                    .setMessage("Has añadido una pregunta a la base de datos, pero para que tu pregunta aparezca en las tarjetas debe ser aprobada primero por los moderadores.")
                    .setPositiveButton("Entendido") { _, _ -> findNavController().popBackStack() }
                    .show()
            }

            val showErrorDialog = { msg: String? ->
                AlertDialog.Builder(requireContext()).setTitle("Error")
                    .setMessage("Ha habido un error intentando añadir tu preunta a la base de datos:\n\n$msg")
                    .setPositiveButton("Aceptar") { _, _ -> }
                    .show()
            }

            // POST con los datos de las preguntas al servidor
            Requester.postJson("/submit-question", jsonReq, { json ->
                if (json.getBoolean("success"))
                    showSuccessDialog()
                else
                    showErrorDialog(json.getString("msg"))
            }, { error ->
                showErrorDialog(error)
            })
        }
    }
}