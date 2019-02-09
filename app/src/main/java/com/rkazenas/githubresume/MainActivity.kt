package com.rkazenas.githubresume

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rkazenas.githubresume.api.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val service by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generate_bt.setOnClickListener {
            val userName = user_et.text
            conectToApi(userName.trim().toString())

        }
    }

    private fun conectToApi(userName: String) {
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
            observable = service.getUserInfo(userName),
            onSuccess = {
                Log.d("user", it.login)
                val intent = Intent(this, UserActivity::class.java)
                intent.putExtra("userInfo", it)
                startActivity(intent)
            },
            onError = {
                it.printStackTrace()
            }
        )
    }
}