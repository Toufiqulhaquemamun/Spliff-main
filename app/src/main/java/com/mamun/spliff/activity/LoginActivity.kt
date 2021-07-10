package com.mamun.spliff.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.mamun.spliff.R
import com.mamun.spliff.model.LoginResponse
import com.mamun.spliff.model.User
import com.mamun.spliff.repository.LoginListener
import com.mamun.spliff.repository.LoginRepo
import com.mamun.spliff.utils.UserInteraction
import java.lang.Exception

class LoginActivity : AppCompatActivity() , LoginListener{
    private var btnLogin : Button? =null
    private var etEmail : EditText? =null
    private var etPass : EditText? =null
    private var progress : ProgressBar? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView();

        btnLogin!!.setOnClickListener {
            if (validate()) {
                UserInteraction.setFlag(this@LoginActivity)
//                btnLogin!!.isEnabled = false
//                progress!!.visibility = View.VISIBLE
                try {
                    login()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        }
    }

    private fun login() {
        val name = etEmail!!.text.toString().trim { it <= ' ' }
        val password = etPass!!.text.toString().trim { it <= ' ' }
        val user = User()
        user.email = name
        user.password = password

        LoginRepo.userLogin(user, this)


    }

    private fun validate(): Boolean {
        var valid = true
        val userName = etEmail!!.text.toString().trim { it <= ' ' }
        val password = etPass!!.text.toString().trim { it <= ' ' }
        if (userName.isEmpty()) {
            etEmail!!.error = "Enter email"
            etEmail!!.requestFocus()
            valid = false
        } else if (password.isEmpty()) {
            etPass!!.error = "Enter Password"
            etPass!!.requestFocus()
            valid = false
        }
        return valid
    }

    private fun initView() {
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etpass);
        progress = findViewById(R.id.progress);
    }

    override fun getLoginInfo(userinfo: LoginResponse?, error: String?) {
        if(userinfo!=null)
        {
           var token : String? = userinfo.success?.token
            if (token != null) {
                 Log.d("TAG", "getLoginInfo: "+token)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        else
        {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
        UserInteraction.clearFlag(this@LoginActivity)
    }
}