package com.mamun.spliff.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.mamun.spliff.R

class Wlcmctivity : AppCompatActivity() {
    private var btnLogin: Button? = null
    private var btnReg: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wlcmctivity)

        btnLogin= findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnReg);

        btnLogin!!.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnReg!!.setOnClickListener {
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }
    }
}