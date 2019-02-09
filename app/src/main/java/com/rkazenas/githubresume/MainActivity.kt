package com.rkazenas.githubresume

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rkazenas.githubresume.api.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private val service by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun <T> fetchSubscribe(
            observable: Observable<T>,
            onSuccess: (T) -> Unit,
            onError: (Throwable) -> Unit
        ): Disposable {
            return observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(onSuccess, onError)
        }

        fetchSubscribe(
            observable = service.getUserInfo("mantykora"),
            onSuccess = {
                Log.d("user", it.login)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}