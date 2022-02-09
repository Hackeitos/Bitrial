package com.bitrial.bitrial

import android.animation.ObjectAnimator
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bitrial.bitrial.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animation = ObjectAnimator.ofFloat(binding.backgroundImg, "rotation", 0.0f, 360f)
        animation.duration = 80000
        animation.interpolator = LinearInterpolator()
        animation.repeatCount = ObjectAnimator.INFINITE
        animation.start()

        binding.buttonPlay.setOnClickListener {
            findNavController().navigate(R.id.cardFragment)
        }

        binding.buttonAddQuestion.setOnClickListener {
            findNavController().navigate(R.id.submitQuestionFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}