package com.geekbrains.februarymaterial.view.animations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentAnimationsRotateFabBinding

class AnimationFragmentRotateFab: Fragment() {

    companion object {
        fun newInstance() = AnimationFragmentRotateFab()
    }

    private var _binding: FragmentAnimationsRotateFabBinding? = null
    private val binding get() = _binding!!

    val duration = 1000L
    var flag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimationsRotateFabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextFragment()
        rotateFab()
    }

    private fun nextFragment(){
        binding.optionTwoContainer.setOnClickListener {
             requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                 //анимация переходы
                 R.anim.slide_in,
                 R.anim.fade_out,
                 R.anim.fade_in,
                 R.anim.slide_out
             ).replace(R.id.container_main_activity, AnimationFragmentListAnimator.newInstance()).addToBackStack("").commit()
        }
        binding.optionOneContainer.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().setCustomAnimations(
                //анимация переходы
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            ).replace(R.id.container_main_activity,AnimationBonus.newInstance()).addToBackStack("").commit()
        }
    }

        private fun rotateFab(){
        binding.fab.setOnClickListener {
        flag = !flag
        if(flag){
            ObjectAnimator.ofFloat( binding.plusImageview,View.ROTATION,0f,405f).setDuration(duration).start()
            ObjectAnimator.ofFloat( binding.optionOneContainer,View.TRANSLATION_Y,-50f,-260f).setDuration(duration).start()
            ObjectAnimator.ofFloat( binding.optionTwoContainer,View.TRANSLATION_Y,-20f,-130f).setDuration(duration).start()

            binding.optionOneContainer.animate()
                .alpha(1f)
                .setDuration(duration*4)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.optionOneContainer.isClickable = true
                    }
                })
            binding.optionTwoContainer.animate()
                .alpha(1f)
                .setDuration(duration*4)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.optionTwoContainer.isClickable = true
                    }
                })

            binding.transparentBackground.animate()
                .alpha(0.8f)
                .setDuration(duration)
        }else{
            ObjectAnimator.ofFloat( binding.plusImageview,View.ROTATION,405f,0f).setDuration(duration).start()
            ObjectAnimator.ofFloat( binding.optionOneContainer,View.TRANSLATION_Y,-260f,-50f).setDuration(duration).start()
            ObjectAnimator.ofFloat( binding.optionTwoContainer,View.TRANSLATION_Y,-130f,-20f).setDuration(duration).start()

            binding.optionOneContainer.animate()
                .alpha(0f)
                .setDuration(duration/2)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.optionOneContainer.isClickable = false
                    }
                })
            binding.optionTwoContainer.animate()
                .alpha(0f)
                .setDuration(duration/2)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        binding.optionTwoContainer.isClickable = false
                    }
                })
            binding.transparentBackground.animate()
                .alpha(0f)
                .setDuration(duration)
            }
        }
    }

}