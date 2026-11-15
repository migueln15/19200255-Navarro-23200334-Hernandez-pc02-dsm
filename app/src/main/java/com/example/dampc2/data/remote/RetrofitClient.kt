package com.example.dampc2.data.remote

import com.example.dampc2.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Configuración común de Retrofit.
 *
 * APOD y NeoWs están en servidores distintos, por eso hay dos instancias:
 * - [apod]: https://science.nasa.gov/  (opción Imágenes)
 * - [neoWs]: https://api.nasa.gov/     (opción Asteroides)
 *
 * El parámetro api_key se agrega automáticamente a todas las peticiones,
 * así que las interfaces de cada opción no necesitan declararlo.
 *
 * Uso: RetrofitClient.apod.create(MiApi::class.java)
 */
object RetrofitClient {

    private const val APOD_BASE_URL = "https://science.nasa.gov/"
    private const val NEOWS_BASE_URL = "https://api.nasa.gov/"

    private val apiKeyInterceptor = Interceptor { chain ->
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.NASA_API_KEY)
            .build()
        chain.proceed(request.newBuilder().url(url).build())
    }

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    private fun crearRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apod: Retrofit by lazy { crearRetrofit(APOD_BASE_URL) }

    val neoWs: Retrofit by lazy { crearRetrofit(NEOWS_BASE_URL) }
}
