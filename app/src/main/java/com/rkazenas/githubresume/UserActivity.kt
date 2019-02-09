package com.rkazenas.githubresume

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rkazenas.githubresume.api.UserData
import com.rkazenas.githubresume.utils.GlideApp
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val user = intent.getParcelableExtra<UserData>("userInfo")
        Log.d("userActivity", user.login)

        login_tv.text = user.login

        webpage_bt.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(user.html_url))
            startActivity(browserIntent)
        }

        GlideApp
            .with(this)
            .load(user.avatar_url)
            .circleCrop()
            .placeholder(R.color.colorPrimary)
            .override(400, 400)
            .into(avatar_iv)
    }
}
