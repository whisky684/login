package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*


class register : AppCompatActivity() {
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            val it = Intent(this, member::class.java)
            startActivity(it)
            finish()
        }
        btreg.setOnClickListener {
            val email = regmail.text.toString().trim()
            val pass = regpass.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "กรุณากรอก Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                Toast.makeText(this, "กรุณากรอกPassword", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            auth!!.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (pass.length < 8) {
                        regpass.error = "กรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร"
                    } else {
                        Toast.makeText(
                            this,
                            " Login ล้มเหลว เนื่องจาก : " + task.exception!!.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    regmail.setText("")
                    regpass.setText("")
                } else {
                    Toast.makeText(this, " Register Success! ", Toast.LENGTH_LONG).show()
                    val log = Intent(this, member::class.java)
                    startActivity(log)
                    finish()
                }
            }
        }
        btlog.setOnClickListener {
            val it = Intent(this, member::class.java)
            startActivity(it)
        }

    }
}