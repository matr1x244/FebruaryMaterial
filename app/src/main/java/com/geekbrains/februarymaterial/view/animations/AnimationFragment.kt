package com.geekbrains.februarymaterial.view.animations

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.geekbrains.februarymaterial.databinding.FragmentAnimationsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class AnimationFragment: Fragment() {

    companion object {
        fun newInstance() = AnimationFragment()
    }

    private var _binding: FragmentAnimationsBinding? = null
    private val binding get() = _binding!!

    /*Выдвижная панель*/
    private lateinit var bottomSheetBehavior : BottomSheetBehavior<ConstraintLayout> //<Всегда нужен контейнер>

    var flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //animationFade()
        animationSlide()
    }


    private fun animationFade() {
        binding.button.setOnClickListener {
            val transition = TransitionSet()
            val fade = Fade()
            fade.duration = 5000
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            transition.ordering = TransitionSet.ORDERING_SEQUENTIAL
            transition.addTransition(fade)
            transition.addTransition(changeBounds)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer,transition)
            flag = !flag
            binding.text.visibility =  if(flag) View.VISIBLE else View.GONE
        }
    }

    private fun animationSlide() {
        binding.button.setOnClickListener {
            val transition = TransitionSet()
            val slide = Slide()
            slide.duration = 5000
            slide.slideEdge = Gravity.BOTTOM
            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000
            transition.ordering = TransitionSet.ORDERING_TOGETHER
            transition.addTransition(slide)
            transition.addTransition(changeBounds)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer,transition)
            flag = !flag
            binding.text.visibility =  if(flag) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}