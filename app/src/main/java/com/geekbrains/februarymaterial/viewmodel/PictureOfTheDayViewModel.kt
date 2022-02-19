package com.geekbrains.februarymaterial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.februarymaterial.BuildConfig
import com.geekbrains.februarymaterial.repository.PictureOfTheDayResponseData
import com.geekbrains.februarymaterial.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(private val liveData: MutableLiveData<PictureOfTheDayAppState> = MutableLiveData(),
                               private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl())
    :ViewModel() {
    fun getLiveData(): LiveData<PictureOfTheDayAppState> {
        return  liveData
    }

    fun sendServerRequest(){
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(BuildConfig.NASA_API_KEY).enqueue(
            object : Callback <PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call <PictureOfTheDayResponseData>,
                    response: Response <PictureOfTheDayResponseData>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayAppState.Success(it))
                        }
                    }else{
                        //TODO HW SNACKBAR можно
                    }
                }
                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    //TODO HW не доступен сервер
                }

            }
        )
    }
}