package com.abhi.messenger2

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abhi.messenger2.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    private lateinit var mDBref : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tologin.setOnClickListener {
           // onBackPressed()
            finish()
        }

        binding.signupbtn.setOnClickListener(View.OnClickListener {
            if(validatedetails()){
                var mfirebaseAuth = FirebaseAuth.getInstance()



                val email = binding.emailsignup.text.toString()
                val pass = binding.passsignup.text.toString()

                mfirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener {
                    if(it.isSuccessful)
                    {

                        //adding user to database
                        val uid = mfirebaseAuth.uid
                        val name = binding.name.text.toString()

                        addUserTodatabase(name,email,uid)

                        onBackPressed()
                    }else
                    {
                        Toast.makeText(this,"Failed to create Account", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })


    }

    private fun addUserTodatabase(name: String, email: String, uid: String?) {

        mDBref = FirebaseDatabase.getInstance().getReference()

        Toast.makeText(this,"Database Creating ....", Toast.LENGTH_SHORT).show()


        //user is parent node
        mDBref.child("user").child(uid!!).setValue(User(name,email,uid)).addOnCompleteListener {
            if(it.isSuccessful)
            {
                Toast.makeText(this,"Database Created", Toast.LENGTH_SHORT).show()

            }
            else
            {
                Toast.makeText(this,"Database Creation Failed ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun validatedetails(): Boolean {
        return when{

            TextUtils.isEmpty(binding.emailsignup.text.toString().trim(){ it<=' '})-> {
                return false
            }
            TextUtils.isEmpty(binding.passsignup.text.toString().trim(){ it<=' '}) -> {
                return false
            }

            else->{
                return true
            }

        }
    }
}