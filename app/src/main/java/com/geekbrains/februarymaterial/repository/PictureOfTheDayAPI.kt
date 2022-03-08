package com.geekbrains.februarymaterial.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String
    ): Call <PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheYesterday(
        @Query("api_key") apiKey: String,
        @Query("date") date: String?
    ): Call <PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheNewYear(
        @Query("api_key") apiKey: String,
        @Query("date") date: String?
    ): Call <PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheDayEarth(
        @Query("api_key") apiKey: String,
        @Query("date") date: String?
    ): Call <PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheDayMars(
        @Query("api_key") apiKey: String,
        @Query("date") date: String?
    ): Call <PictureOfTheDayResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheDaySystem(
        @Query("api_key") apiKey: String,
        @Query("date") date: String?
    ): Call <PictureOfTheDayResponseData>


    @GET("asset/{nasaId}")
    fun getNasaImage(
        @Path("nasaId") nasaId: String
    ): Call<PictureOfTheDayResponseData>


}