package com.abhi.messenger2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context : Context, val userList : ArrayList<User>)
    : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentuser  = userList[position]

        holder.name.text = currentuser.name

        holder.name.setOnClickListener(View.OnClickListener {


            val intent = Intent(context.applicationContext,ChatActivity::class.java)
            intent.putExtra("name", currentuser.name)
            intent.putExtra("uid", currentuser.uid)

            context.startActivity(intent)

        })


    }

    override fun getItemCount(): Int {
        return userList.size;
    }


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val name = itemView.findViewById<TextView>(R.id.username)

    }
}