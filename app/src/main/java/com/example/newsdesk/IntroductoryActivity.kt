package com.example.newsdesk

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class IntroductoryActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.introductory_activity)


        Handler(Looper.getMainLooper()).also { handler = it }
        handler.postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            },
            3000
        )

    }

}