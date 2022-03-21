package com.geekbrains.februarymaterial.view.animations

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.*
import com.geekbrains.februarymaterial.R
import com.geekbrains.februarymaterial.databinding.FragmentAnimationsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class AnimationFragment: Fragment() {

    companion object {
        fun newInstance() = AnimationFragment()
    }

    private var _binding: FragmentAnimationsBinding? = null
    private val binding get() = _binding!!

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
        //animationSlide()
        zoomImageClick()
        //buttonRace()
        textRandomShuffle()
        rotateFab()
        nextFragment()
    }

    private fun nextFragment() {
        binding.plusImageview.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_main_activity, AnimationFragmentRotateFab.newInstance())
                .addToBackStack("null")
                .commit()
        }
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

    private fun zoomImageClick() {
        binding.image.setOnClickListener {
            val changeBounds = ChangeImageTransform()
            changeBounds.duration = 3000
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, changeBounds)
            flag = !flag
            binding.image.scaleType = if (flag) ImageView.ScaleType.FIT_START else ImageView.ScaleType.FIT_END
        }
    }

    private fun buttonRace(){
        binding.button.setOnClickListener {
        val changeBounds = ChangeBounds()
        changeBounds.duration= 5000
        changeBounds.setPathMotion(ArcMotion())
        TransitionManager.beginDelayedTransition(binding.root,changeBounds)
        flag = !flag
        val params = binding.button.layoutParams as LinearLayout.LayoutParams
        params.gravity = if(flag) Gravity.BOTTOM or Gravity.START else Gravity.CENTER or Gravity.END
        binding.button.layoutParams = params
    }
}

    private fun textRandomShuffle(){
        val titles: MutableList<String> = ArrayList()
        titles.add(0,"рэндом тест")

        repeat(5) {
            titles.add("item $it")
        }
        binding.button.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.textRandomConteiner)
            titles.shuffle()
            binding.textRandomConteiner.removeAllViews()
            titles.forEach { title:String->
                binding.textRandomConteiner.addView(TextView(context).apply {
                    text = title
                    ViewCompat.setTransitionName(this,title)
                })
            }
        }
    }

    private fun rotateFab(){
        var duration = 1000L
        binding.fabRotateFragment.setOnClickListener {
        flag = !flag
        if (flag) {
            ObjectAnimator.ofFloat(binding.fabRotateFragment, View.ROTATION, 0f, 405f)
                .setDuration(duration).start()
            } else{
            ObjectAnimator.ofFloat( binding.fabRotateFragment,View.ROTATION,405f,0f)
                .setDuration(duration).start()
            ObjectAnimator.ofFloat( binding.plusImageview,View.ROTATION,405f,0f)
                .setDuration(duration).start()
            ObjectAnimator.ofFloat( binding.image,View.ROTATION,850f,180f)
                .setDuration(duration).start()
            }
         }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}