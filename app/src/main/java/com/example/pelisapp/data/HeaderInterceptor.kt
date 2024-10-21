package com.example.pelisapp.data

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val apiKey: String,private val lenguage:String):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url

        // Modificar la URL para agregar el api_key
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", apiKey) // Nombre del query parameter
            .addQueryParameter("language", lenguage) // Nombre del query parameter
            .build()

        // Crear un nuevo request con la URL modificada
        val requestWithApiKey = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(requestWithApiKey)
    }
}