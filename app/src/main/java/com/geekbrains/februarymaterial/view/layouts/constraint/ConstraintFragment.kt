package com.geekbrains.februarymaterial.view.layouts.constraint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.februarymaterial.databinding.FragmentConstraintBinding
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayAppState
import com.geekbrains.februarymaterial.viewmodel.PictureOfTheDayViewModel
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener


class ConstraintFragment : Fragment() {

    private var _binding: FragmentConstraintBinding? = null
    private val binding: FragmentConstraintBinding
        get() = _binding!!

    private val viewModel: PictureOfTheDayViewModel by lazy { ViewModelProvider(this).get(
        PictureOfTheDayViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConstraintBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        testConstraint()
        testServer()
        testHelpButton()

    }

    private fun testHelpButton() {
        binding.btnTwo.setOnClickListener {
            val builder = GuideView.Builder(requireContext())
                .setTitle("Стиль не очень")
                .setContentText("А это всего лишь подсказка")
                .setGravity(Gravity.center)
                .setDismissType(DismissType.anywhere)
                .setTargetView(binding.btnTwo)
                .setDismissType(DismissType.anywhere)
                .setGuideListener(object : GuideListener {

                    override fun onDismiss(view: View?) {
                        val builder2 = GuideView.Builder(requireContext())
                            .setTitle("Новый(material) подход")
                            .setContentText("Нормальная подсказка")
                            .setGravity(Gravity.center)
                            .setDismissType(DismissType.targetView)
                            .setTargetView(binding.btnOne)
                            .setDismissType(DismissType.targetView)
                            .setGuideListener(object : GuideListener {
                                override fun onDismiss(view: View?) {
                                    // сохранить в SP то что уже показали, и больше не надо
                                }
                            })
                        builder2.build().show()
                    }
                })
            builder.build().show()
        }
    }

    private fun testServer() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.sendServerRequest()
    }

   private fun renderData(pictureOfTheDayAppState: PictureOfTheDayAppState) {
        when (pictureOfTheDayAppState) {
            is PictureOfTheDayAppState.Error -> {
                binding.fragmentConstraint.visibility = View.GONE
            }
            is PictureOfTheDayAppState.Loading -> {
                //....
            }
            is PictureOfTheDayAppState.Success -> {
                binding.textView.text = pictureOfTheDayAppState.serverResponseData.title
                binding.btnOne.text = pictureOfTheDayAppState.serverResponseData.date
                binding.fragmentConstraint.visibility = View.VISIBLE
            }
        }
    }

    private fun testConstraint() {
        binding.groupBtn.visibility = View.VISIBLE
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConstraintFragment()
    }



}