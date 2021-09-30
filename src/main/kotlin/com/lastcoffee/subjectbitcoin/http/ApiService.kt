package com.lastcoffee.subjectbitcoin.http


import com.google.gson.JsonObject
import com.lastcoffee.subjectbitcoin.bean.BaseRepose
import com.lastcoffee.subjectbitcoin.bean.NewPriceBean
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.net.InetSocketAddress
import java.net.Proxy

fun okhttpClientBuilder(): OkHttpClient {
    println("开始构建新的Okhttp")
    return OkHttpClient.Builder()

        .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("127.0.0.1", 7890))).build()
}

fun getApiServer():ApiService = Retrofit.Builder()
    .baseUrl("https://www.okex.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttpClientBuilder())
    .build().create(ApiService::class.java)

interface ApiService {

    @GET("/api/v5/market/index-tickers")
    suspend fun getCoinNewPrice(@Query("instId")instId:String):BaseRepose<NewPriceBean>
    @FormUrlEncoded
    @POST("https://sctapi.ftqq.com/SCT57600TAas9na4XRvP5zOzbThE6NH0V.send")
    suspend fun sendNotification(@Field("title")title:String, @Field("desp")desp:String):JsonObject
}