package com.abhi.messenger2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhi.messenger2.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    lateinit var binding : ActivityChatBinding
    lateinit var messageList : ArrayList<Message>
    lateinit var messageadapter : MessageAdapter

    var ReceieverRoom : String? = null
    var SenderRoom : String? = null

    lateinit var mDBref : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val v = binding.root
        setContentView(v)

        mDBref = FirebaseDatabase.getInstance().getReference()


        val name = intent.getStringExtra("name")
        val reciveruid  = intent.getStringExtra("uid")

        supportActionBar?.title = name

        val senderuid = FirebaseAuth.getInstance().currentUser?.uid

        SenderRoom = reciveruid + senderuid
        ReceieverRoom = senderuid + reciveruid




        messageList = ArrayList()
        messageadapter = MessageAdapter(this,messageList)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = messageadapter

        //logic for adding data to recyckerview
        mDBref.child("chats").child(SenderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()
                    for(postsnapshot in snapshot.children)
                    {
                        val messageObject = postsnapshot.getValue(Message::class.java)
                        messageList.add(messageObject!!)
                    }

                    messageadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })



        binding.sentbtn.setOnClickListener {
             //we have to send the message to Database
            //we have send message to both sender room and receiver room

            val message = binding.message.text.toString()
            val messageObject = Message(message,senderuid)

            mDBref.child("chats").child(SenderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {

                    mDBref.child("chats").child(ReceieverRoom!!).child("messages").push()
                        .setValue(messageObject)

                }

            //also update the receiverroom
            binding.message.text = null

        }



    }


}