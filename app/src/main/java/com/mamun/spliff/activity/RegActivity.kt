package com.mamun.spliff.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.mamun.spliff.R
import com.mamun.spliff.model.Success
import com.mamun.spliff.model.User
import com.mamun.spliff.repository.LoginRepo
import com.mamun.spliff.repository.RegListener
import com.mamun.spliff.repository.RegRepo
import com.mamun.spliff.utils.UserInteraction
import java.lang.Exception

class RegActivity : AppCompatActivity(), RegListener {

    private var btnReg : Button? =null
    private var etEmail : EditText? =null
    private var etPass : EditText? =null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        initView()

        btnReg?.setOnClickListener {
            if (validate()) {
                UserInteraction.setFlag(this@RegActivity)

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
        user.c_password=password
        user.name="none"

        RegRepo.userReg(user, this)


    }


    private fun initView() {
        btnReg = findViewById(R.id.btnReg);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etpass);

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

    override fun getRegInfo(userinfo: Success?, error: String?) {
        if(userinfo!=null)
        {
            var token : String? = userinfo.token
                Log.d("TAG", "getLoginInfo: "+token)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

        }
        else
        {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
        UserInteraction.clearFlag(this@RegActivity)
    }
}