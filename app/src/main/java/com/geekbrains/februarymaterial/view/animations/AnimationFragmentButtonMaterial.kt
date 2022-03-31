package com.geekbrains.februarymaterial.view.animations

import com.geekbrains.februarymaterial.databinding.FragmentAnimationsButtonMaterialBinding
import com.geekbrains.februarymaterial.extensionFun.viewBindingFragment.ViewBindingFragment

class AnimationFragmentButtonMaterial: ViewBindingFragment<FragmentAnimationsButtonMaterialBinding>(FragmentAnimationsButtonMaterialBinding::inflate) {

    companion object {
        fun newInstance() = AnimationFragmentButtonMaterial()
    }

}