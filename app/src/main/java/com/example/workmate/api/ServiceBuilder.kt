package com.example.workmate.api

import androidx.core.net.toUri
import com.example.workmate.entity.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    //private const val BASE_URL = "http://10.0.2.2:5000/api/"
    private const val BASE_URL = "http://192.168.100.8:5000/api/"
   //private const val BASE_URL = "http://localhost:5000/api/"

    var token: String? = null
    var jid: String? = null
    var jfuid: String? = null
    var LoggedInUser: String? = null

    private val okHTTP = OkHttpClient.Builder();
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHTTP.build())
    private val retrofit = retrofitBuilder.build();

    fun <T> buildServices(serviceType: Class<T>): T {
        return retrofit.create(serviceType);
    }

    fun loadFilePath(): String {
        val arr = BASE_URL.split("/").toTypedArray()
        return arr[0] + "/" + arr[1] + arr[2] + "/uploads/";
    }
}