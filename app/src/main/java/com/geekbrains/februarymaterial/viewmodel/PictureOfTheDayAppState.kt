package com.geekbrains.februarymaterial.viewmodel

import com.geekbrains.februarymaterial.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayAppState{

    data class Success(val serverResponseData: PictureOfTheDayResponseData) : PictureOfTheDayAppState()

    data class Error(val error: Throwable) : PictureOfTheDayAppState()

    data class Loading(val progress: Int?) : PictureOfTheDayAppState()

}


