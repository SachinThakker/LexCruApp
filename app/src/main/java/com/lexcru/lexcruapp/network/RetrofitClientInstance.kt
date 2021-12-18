package com.lexcru.lexcruapp.network

import com.lexcru.lexcruapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClientInstance {

    private val BASE_URL = "http://fitness.kriyaninfotech.com/ferrybooking/"
    public val PRIVACY_POLICY_URL = "http://mybuug.com/etdfgs/privacy-policy"
    public val DEEPLINK_BASE_URL = "https://www.mybuug.com/"

    fun getRetrofitInstance(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getOKHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return retrofit
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return getRetrofitInstance().create(serviceClass)
    }

    fun getOKHttpClient():OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
//        val credential: String = Credentials.basic("admin", "123456")
        val builder = OkHttpClient().newBuilder()
            .addInterceptor(logging)
            .authenticator(object : Authenticator {
                override fun authenticate(route: Route?, response: Response): Request? {
                    return response.request().newBuilder()
                        .header("Content-Type", "application/json")
//                        .header("Authorization", credential)
//                        .header("Connection", "close")
                        .build()
                }

            })
            .connectTimeout(1, TimeUnit.MINUTES) // connection timeout
            .readTimeout(1, TimeUnit.MINUTES)    // read timeout
            .writeTimeout(1, TimeUnit.MINUTES)   // write timeout

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        return builder.build()
    }
}