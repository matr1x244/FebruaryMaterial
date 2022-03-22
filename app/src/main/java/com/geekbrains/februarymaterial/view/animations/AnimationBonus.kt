package com.geekbrains.februarymaterial.view.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import coil.load
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentAnimationsBonusStartBinding
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel

class AnimationBonus: Fragment() {

    companion object {
        fun newInstance() = AnimationBonus()
    }

    private var _binding: FragmentAnimationsBonusStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(
        PictureOfTheDayViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimationsBonusStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bonusAnimator()
        testServer()

    }

    private fun testServer() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequest()
    }

    private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {
                //....
            }
            is PictureOfTheDayAppState.Loading -> {
                //....
            }
            is PictureOfTheDayAppState.Success -> {
                binding.title.text = pictureOfTheDayAppState.serverResponseData.title
                binding.date.text = pictureOfTheDayAppState.serverResponseData.date
                binding.description.text = pictureOfTheDayAppState.serverResponseData.copyright
                binding.backgroundImage.load(pictureOfTheDayAppState.serverResponseData.url)
            }
        }
    }

    private fun bonusAnimator (){
        var flag = false
        binding.backgroundImage.setOnClickListener {
            flag = !flag
            if(flag){
                val changeBounds = ChangeBounds()
                changeBounds.interpolator = AnticipateOvershootInterpolator(2.0f)
                changeBounds.duration= 1000L
                TransitionManager.beginDelayedTransition(binding.constraintContainer,changeBounds)

                val constraintSet = ConstraintSet()
                constraintSet.clone(context,R.layout.fragment_animations_bonus_end) //клонируем макет конечный (с учётом констрейтов)
                constraintSet.applyTo(binding.constraintContainer) //вставляем клонируемый макет в корневой макет
            }else{
                val changeBounds = ChangeBounds()
                changeBounds.interpolator = AnticipateOvershootInterpolator(2.0f)
                changeBounds.duration= 1000L
                TransitionManager.beginDelayedTransition(binding.constraintContainer,changeBounds)

                val constraintSet= ConstraintSet()
                constraintSet.clone(context, R.layout.fragment_animations_bonus_start)
                constraintSet.applyTo(binding.constraintContainer)
            }
        }
    }
}