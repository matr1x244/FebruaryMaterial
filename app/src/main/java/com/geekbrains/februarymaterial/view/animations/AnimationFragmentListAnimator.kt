package com.geekbrains.februarymaterial.view.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrains.februarymaterial.databinding.FragmentAnimationsListAnimatorBinding

class AnimationFragmentListAnimator: Fragment() {

    companion object {
        fun newInstance() = AnimationFragmentListAnimator()
    }

    private var _binding: FragmentAnimationsListAnimatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimationsListAnimatorBinding.inflate(inflater, container, false)
        return binding.root
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