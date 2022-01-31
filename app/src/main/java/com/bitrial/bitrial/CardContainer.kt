package com.bitrial.bitrial

import android.R
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bitrial.bitrial.databinding.FragmentCardContainerBinding
import com.bitrial.bitrial.databinding.QuestionLayoutBinding
import android.view.animation.AccelerateDecelerateInterpolator

import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator





class CardContainer(private val card: Card) : Fragment() {

    lateinit var binding: FragmentCardContainerBinding
    lateinit var questionViews: List<QuestionLayoutBinding>
    private var answerMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCardContainerBinding.inflate(inflater, container, false).apply {
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

    fun flipCard() {
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
    }

    private fun updateCard() {
        questionViews.forEach { qBinding ->
            val category =
                Categoria.valueOf(resources.getResourceEntryName(qBinding.root.id).uppercase())
            val question = card.categories[category]!!

            qBinding.image.setImageResource(category.icon)
            val colorString = TypedValue()
            requireContext().theme.resolveAttribute(category.color, colorString, true)

            qBinding.image.imageTintList =
                ColorStateList.valueOf(Color.parseColor("${colorString.coerceToString()}"))

            if (answerMode)
                qBinding.question.text = question.respuesta
            else
                qBinding.question.text = question.pregunta

        }
    }


}