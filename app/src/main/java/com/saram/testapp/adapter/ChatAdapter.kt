package com.saram.testapp.adapter


import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.saram.testapp.ChatActivity
import com.saram.testapp.EditActivity
import com.saram.testapp.R
import com.saram.testapp.data.ChatData


class ChatAdapter(
    val mContext : Context, val mList : List<ChatData>
) : RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ChatData) {

            val contentTxt = itemView.findViewById<TextView>(R.id.contentTxt_1)
            val contentTxt_1 = itemView.findViewById<TextView>(R.id.time_1)

            contentTxt.text = item.content
            contentTxt_1.text = item.time
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}