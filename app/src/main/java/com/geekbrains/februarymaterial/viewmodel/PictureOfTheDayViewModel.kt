package com.geekbrains.februarymaterial.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.februarymaterial.BuildConfig
import com.geekbrains.februarymaterial.repository.PictureOfTheDayResponseData
import com.geekbrains.februarymaterial.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

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
                        liveData.postValue(PictureOfTheDayAppState.Loading(null))
                    }
                }
                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    //TODO HW не доступен сервер
                }

            }
        )
    }

    fun sendServerAstronautDay(){

        val dateAstronaut = "2017-12-31"

        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheNewYear(BuildConfig.NASA_API_KEY,dateAstronaut).enqueue(
            object : Callback <PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponseData>,
                    response: Response<PictureOfTheDayResponseData>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayAppState.Success(it))
                        }
                    }else{
                        liveData.postValue(PictureOfTheDayAppState.Loading(null))
                    }
                }
                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
        )
    }



    fun sendServerYesterday(){
        liveData.postValue(PictureOfTheDayAppState.Loading(null))

        val calendar = Calendar.getInstance()
        val dayOfCurrent =  calendar[Calendar.DATE]
        Log.d("dayOfBack", dayOfCurrent.toString())

        val backDay = dayOfCurrent - 1 // вчерашний день
        val dateYesterday: String = SimpleDateFormat("yyyy-MM-$backDay", Locale.getDefault()).format(Date())
        Log.d("currentDate", dateYesterday)

        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheYesterday(BuildConfig.NASA_API_KEY,dateYesterday).enqueue(
            object : Callback <PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponseData>,
                    response: Response<PictureOfTheDayResponseData>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayAppState.Success(it))
                        }
                    }else{
                        liveData.postValue(PictureOfTheDayAppState.Loading(null))
                    }
                }
                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
        )
    }

    fun sendServerEarthDay(){
        val dateEarth = "2022-03-13"
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDayEarth(BuildConfig.NASA_API_KEY,dateEarth).enqueue(
            object : Callback <PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponseData>,
                    response: Response<PictureOfTheDayResponseData>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayAppState.Success(it))
                        }
                    }else{
                        liveData.postValue(PictureOfTheDayAppState.Loading(null))
                    }
                }
                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
        )
    }

    fun sendServerMarsDay(){
        val dateMars = "2009-04-24"
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDayMars(BuildConfig.NASA_API_KEY,dateMars).enqueue(
            object : Callback <PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponseData>,
                    response: Response<PictureOfTheDayResponseData>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayAppState.Success(it))
                        }
                    }else{
                        liveData.postValue(PictureOfTheDayAppState.Loading(null))
                    }
                }
                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
        )
    }

    fun sendServerSystemDay(){
        val dateSystem = "2022-03-16"
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDaySystem(BuildConfig.NASA_API_KEY,dateSystem).enqueue(
            object : Callback <PictureOfTheDayResponseData> {
                override fun onResponse(
                    call: Call<PictureOfTheDayResponseData>,
                    response: Response<PictureOfTheDayResponseData>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayAppState.Success(it))
                        }
                    }else{
                        liveData.postValue(PictureOfTheDayAppState.Loading(null))
                    }
                }
                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    //TODO("Not yet implemented")
                }
            }
        )
    }

}