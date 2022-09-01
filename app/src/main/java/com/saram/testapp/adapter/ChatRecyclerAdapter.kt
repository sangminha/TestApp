package com.saram.testapp.adapter


import com.saram.testapp.data.ChatData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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
            val realtime =
                FirebaseDatabase.getInstance(" https://testapp-80b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val contentTxt = itemView.findViewById<TextView>(R.id.contentTxt)
            val time = itemView.findViewById<TextView>(R.id.time)
            val button = itemView.findViewById<Button>(R.id.addReplyBtn12)
            contentTxt.text = item.content
            time.text = item.time
            button.setOnClickListener {
                val edit = itemView.findViewById<EditText>(R.id.contentEdt)
                val content = edit.text.toString()

                val sdf = SimpleDateFormat("a h:mm")
                val now = Calendar.getInstance()
                val nowStr = sdf.format(now.time)
                val deviceToken = "123321"
                val inputMap = HashMap<String, String>()
                inputMap["content"] = content
                inputMap["deviceToekn"] = deviceToken
                inputMap["time"] = nowStr


                realtime.getReference("data")
                    .child("meassge")
                    .setValue(inputMap)

                edit.setText("")

                realtime.getReference("data").child("meassge").addValueEventListener(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val chatData = ChatData(
                                snapshot.child("content").value.toString(),
                                snapshot.child("time").value.toString(),
                                snapshot.child("deviceToken").value.toString()
                            )

                        }

                        override fun onCancelled(error: DatabaseError) {

                        }
                    })
//            수정 버튼 클릭 이벤트


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