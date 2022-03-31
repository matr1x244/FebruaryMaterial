package com.geekbrains.februarymaterial.view.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.databinding.FragmentAnimationsBinding
import com.geekbrains.februarymaterial.databinding.FragmentAnimationsListAnimatorBinding
import com.geekbrains.februarymaterial.extensionFun.viewBindingFragment.ViewBindingFragment

class AnimationFragmentListAnimator: ViewBindingFragment<FragmentAnimationsListAnimatorBinding>(FragmentAnimationsListAnimatorBinding::inflate) {

    companion object {
        fun newInstance() = AnimationFragmentListAnimator()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scrollViewFun()
    }


    private fun scrollViewFun() {
        binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            binding.header.isSelected = binding.scrollView.canScrollVertically(-1) //задаём header параметры скрола если ушло вниз появилась тень
        }
    }
}