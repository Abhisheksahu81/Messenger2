package com.abhi.messenger2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.abhi.messenger2.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mcurrentuser = FirebaseAuth.getInstance().currentUser
        if(mcurrentuser != null)
        {
            val intent =  Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }



        binding.loginbtn.setOnClickListener(View.OnClickListener {
            if(validate())
            {
                val email = binding.emailtextfield.text.toString()
                val pass = binding.passtextfield.text.toString()

                var mfirebaseauth = FirebaseAuth.getInstance()
                mfirebaseauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        val intent =  Intent(this,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Snackbar.make(this,binding.root,"User doesn't exists",Snackbar.LENGTH_SHORT).show()

                    }
                }

            }else{

                Snackbar.make(this,binding.root,"Incorrect Details",Snackbar.LENGTH_SHORT).show()

            }
        })

        binding.tosignup.setOnClickListener {
            var intent  = Intent(this@LoginActivity,SignupActivity::class.java)
            startActivity(intent)

        }
    }

    private fun validate(): Boolean {

        return when{
            TextUtils.isEmpty(binding.emailtextfield.text.toString().trim(){ it<=' '})-> {
                return false
            }
            TextUtils.isEmpty(binding.passtextfield.text.toString().trim(){ it<=' '}) -> {
                return false
            }
            else->{
                return true
            }
        }



    }
}