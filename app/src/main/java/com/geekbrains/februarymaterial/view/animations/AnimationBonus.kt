package com.geekbrains.februarymaterial.view.animations

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import coil.load
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentAnimationsBonusStartBinding
import com.geekbrains.februarymaterial.extensionFun.viewBindingFragment.ViewBindingFragment
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel

class AnimationBonus: ViewBindingFragment<FragmentAnimationsBonusStartBinding>(FragmentAnimationsBonusStartBinding::inflate) {

    companion object {
        fun newInstance() = AnimationBonus()
    }

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(
        PictureOfTheDayViewModel::class.java) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bonusAnimatorTwoMaket() //макет start и end
        bonusAnimatorOneMaket() //макет start
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

    private fun bonusAnimatorTwoMaket (){
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

    private fun bonusAnimatorOneMaket(){
        var flag = false
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.constraintContainer)

        binding.backgroundImage.setOnClickListener {
            flag = !flag
            val changeBounds = ChangeBounds()
            changeBounds.interpolator = AnticipateOvershootInterpolator(2.0f)
            changeBounds.duration= 1000L
            changeBounds.addListener(object : Transition.TransitionListener {

                /*Возвращаем после скрытия обратно*/
                override fun onTransitionStart(transition: Transition) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        flag = !flag
                        val changeBounds = ChangeBounds()
                        changeBounds.interpolator = AnticipateOvershootInterpolator(2.0f)
                        changeBounds.duration= 2000L
                        TransitionManager.beginDelayedTransition(binding.constraintContainer,changeBounds)

                        constraintSet.connect(R.id.title,ConstraintSet.END,R.id.backgroundImage,ConstraintSet.START) //соединяем титл
                        constraintSet.clear(R.id.title,ConstraintSet.START)
                        constraintSet.applyTo(binding.constraintContainer)
                    },
                        1000L)
                }

                /*делаем паузу на 1 сек у скрываем*/
                override fun onTransitionEnd(transition: Transition) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        flag = !flag
                        val changeBounds = ChangeBounds()
                        changeBounds.interpolator = AnticipateOvershootInterpolator(2.0f)
                        changeBounds.duration= 1000L
                        TransitionManager.beginDelayedTransition(binding.constraintContainer,changeBounds)

                        constraintSet.connect(R.id.title,ConstraintSet.END,R.id.backgroundImage,ConstraintSet.END) //соединяем титл концом к картинке к началу
                        constraintSet.clear(R.id.title,ConstraintSet.START)
                        constraintSet.applyTo(binding.constraintContainer)
                    },
                        2000L)
                }

                override fun onTransitionCancel(transition: Transition) {
                }

                override fun onTransitionPause(transition: Transition) {
                }

                override fun onTransitionResume(transition: Transition) {
                }

            })

            TransitionManager.beginDelayedTransition(binding.constraintContainer,changeBounds)
            if(flag){
                constraintSet.connect(R.id.title,ConstraintSet.END,R.id.constraint_container,ConstraintSet.END)
                constraintSet.connect(R.id.title,ConstraintSet.START,R.id.constraint_container,ConstraintSet.START)
                constraintSet.setHorizontalBias(R.id.title,0.8f)
                constraintSet.constrainPercentWidth(R.id.title,0.5f)
                //constraintSet.clear(R.id.title,ConstraintSet.END)
                constraintSet.applyTo(binding.constraintContainer)
            }else{
                constraintSet.connect(R.id.title,ConstraintSet.END,R.id.backgroundImage,ConstraintSet.START)
                constraintSet.clear(R.id.title,ConstraintSet.START)
                constraintSet.applyTo(binding.constraintContainer)
            }
        }
    }
}