package com.rkazenas.githubresume

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.rkazenas.githubresume.api.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction



class MainActivity : AppCompatActivity() {
    private val service by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generate_bt.setOnClickListener {
            val userName = user_et.text
            generate_progress.visibility = View.VISIBLE
            conectToApi(userName.trim().toString(), generate_bt)

        }
    }

    private fun conectToApi(userName: String, view: View) {
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
                generate_progress.visibility = View.GONE
            },
            onError = {
                it.printStackTrace()
                generate_progress.visibility = View.GONE

                Snackbar.make(view, "Error in retrieving user data", Snackbar.LENGTH_LONG).show()

            }
        )
    }
}