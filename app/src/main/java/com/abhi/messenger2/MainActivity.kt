package com.abhi.messenger2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abhi.messenger2.databinding.ActivityMainBinding


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var mDbref : DatabaseReference

    lateinit var adapter : UserAdapter
    lateinit var userlist :ArrayList<User>
    lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val v = binding.root
        setContentView(v)


        auth = FirebaseAuth.getInstance()
        userlist = ArrayList()
        adapter = UserAdapter(this,userlist)


        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter


        mDbref = FirebaseDatabase.getInstance().getReference()


        mDbref.child("user").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userlist.clear()
                for(userinfo in snapshot.children)
                {
                    val currentuser = userinfo.getValue(User::class.java)
                    if(auth.currentUser?.uid != currentuser?.uid)
                        userlist.add(currentuser!!)

                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainactivity_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.logout)
        {

            auth.signOut()

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
            return true
        }
        return true
    }
}