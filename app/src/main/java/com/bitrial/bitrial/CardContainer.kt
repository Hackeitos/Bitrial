package com.bitrial.bitrial

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bitrial.bitrial.databinding.FragmentCardContainerBinding
import com.bitrial.bitrial.databinding.QuestionLayoutBinding

import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import androidx.annotation.AttrRes

// Fragment que contiene una tarjeta
class CardContainer(private val card: Card?) : Fragment() {

    constructor() : this(null)

    lateinit var binding: FragmentCardContainerBinding
    lateinit var questionViews: List<QuestionLayoutBinding>
    private var answerMode = false
    private var animBusy = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardContainerBinding.inflate(inflater, container, false).apply {
            // Lista de las views que pueden contener preguntas
            questionViews =
                listOf(geografia, entretenimiento, historia, literatura, ciencias, deportes)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scale = requireContext().resources.displayMetrics.density
        binding.constraintLayout.cameraDistance = 8000 * scale

        updateCard()
    }

    // Metodo para girar la tarjeta y ver el otro lado
    fun flipCard() {
        card ?: return

        if (animBusy)
            return

        animBusy = true
        answerMode = !answerMode

        val animation1 = ObjectAnimator.ofFloat(binding.constraintLayout, "rotationX", 0.0f, 90f)
        animation1.duration = 150
        animation1.interpolator = LinearInterpolator()

        val animation2 = ObjectAnimator.ofFloat(binding.constraintLayout, "rotationX", -90f, 0f)
        animation2.duration = animation1.duration
        animation2.interpolator = animation1.interpolator

        animation1.start()

        animation1.addUpdateListener {
            if (it.currentPlayTime == animation1.duration) {
                updateCard()
                animation2.start()
            }
        }

        animation2.addUpdateListener {
            if (it.currentPlayTime == animation2.duration)
                animBusy = false
        }
    }

    // Actualizar la tarjeta en funcion de si esta en el lado de las preguntas o en el de las respuestas
    // Este metodo tambien se encarga de poner los colores correctos de las categorias
    private fun updateCard() {
        card ?: return

        questionViews.forEach { qBinding ->
            val category =
                Categoria.valueOf(resources.getResourceEntryName(qBinding.root.id).uppercase())
            val question = card.categories[category]!!

            qBinding.image.setImageResource(category.icon)

            qBinding.image.imageTintList = ColorStateList.valueOf(getThemeColor(category.color))

            if (answerMode)
                qBinding.question.text = question.respuesta
            else
                qBinding.question.text = question.pregunta

        }
    }


}