package com.abhi.messenger2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context : Context,val  messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val ITEM_RECIEVE = 1;
    val ITEM_SENT = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //imp hai

        //attaching layout according to view we have

        if(viewType == ITEM_RECIEVE)
        {
            //inflate receive layout
            val view = LayoutInflater.from(context).inflate(R.layout.received,parent,false)
            return receiveViewHolder(view)
        }else
        {
            val view = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return sentViewHolder(view)
        }



    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentmessage = messageList[position]
        if(holder.javaClass == sentViewHolder::class.java)
        {
            val viewHolder = holder as sentViewHolder //typocasting in kotlin
            viewHolder.sentmessage.text  = currentmessage.message

        }else
        {
            val viewHolder = holder as receiveViewHolder
            viewHolder.receivemessage.text  = currentmessage.message

        }

    }

    //extra function that will return viewtype
    override fun getItemViewType(position: Int): Int {
        val currentmessage = messageList[position]


        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderId))
        {
            //inflate sent_layout
            return ITEM_SENT
        }
        else
        {
            //inflate recieve_layout message
            return ITEM_RECIEVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }



    class sentViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {

        val sentmessage = itemview.findViewById<TextView>(R.id.sentmessage)
    }
    class receiveViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {

        val receivemessage = itemview.findViewById<TextView>(R.id.receivemessage)


    }
}