package com.saram.testapp.adapter


import com.saram.testapp.data.ChatData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saram.testapp.*


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
                // alert을 띄우고, alert의 값을 받아와서
                // contentTxt의 text 속성을 변경
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