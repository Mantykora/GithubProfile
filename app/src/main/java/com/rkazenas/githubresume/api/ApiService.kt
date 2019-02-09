package com.rkazenas.githubresume.api

import android.arch.lifecycle.Observer
import android.database.Observable
import android.provider.SyncStateContract
import com.google.gson.GsonBuilder
import io.reactivex.Flowable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{user}")
    fun getUserInfo(@Path("user") user: String) : io.reactivex.Observable<UserData>

    companion object {
        fun create(): ApiService {
            val retrofit =
                    Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                        .baseUrl("https://api.github.com/")
                        .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}