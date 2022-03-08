package com.geekbrains.februarymaterial.repository


import com.google.gson.annotations.SerializedName

data class PictureOfTheDayResponseData(

    @SerializedName("copyright") val copyright: String,
    @SerializedName("date") val date: String,
    @SerializedName("explanation") val explanation: String?,
    @SerializedName("hdurl") val hdurl: String,
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("service_version") val serviceVersion: String,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String
)

data class NasaImageData(
    val collection: Collection?
) {
    data class Collection(
            @SerializedName("href") val href: String?,
            @SerializedName("items") val items: List<Item?>?,
            @SerializedName("version") val version: String?
    )
    {
        data class Item(
            @SerializedName("href") val href: String?
        )
    }
}