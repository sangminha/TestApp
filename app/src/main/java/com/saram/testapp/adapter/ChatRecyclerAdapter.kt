package com.saram.testapp.adapter


import com.saram.testapp.data.ChatData
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.saram.testapp.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class ChatRecyclerAdapter(
    val mContext : Context, val mList : List<ChatData>
) : RecyclerView.Adapter<ChatRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ChatData) {

            val contentTxt = itemView.findViewById<TextView>(R.id.contentTxt)
            val time = itemView.findViewById<TextView>(R.id.time)
            val editBtn = itemView.findViewById<Button>(R.id.addReplyBtn12)

                contentTxt.text=item.content
                time.text=item.time

//            수정 버튼 클릭 이벤트
            editBtn.setOnClickListener {
                val myIntent = Intent(mContext, EditActivity::class.java)
                myIntent.putExtra("chatData", item)
                mContext.startActivity(myIntent)
            }
            itemView.setOnClickListener{
                val myIntent = Intent(mContext, ChatActivity::class.java)
                myIntent.putExtra("content", item)
                mContext.startActivity(myIntent)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.chat_list, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}