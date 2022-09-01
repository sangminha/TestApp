package Adapter


import Data.ChatData
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.saram.testapp.*


class ChatRecyclerAdapter(
    val mContext : Context, val mList : List<ChatData>
) : RecyclerView.Adapter<ChatRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ChatData) {

            val contentTxt = itemView.findViewById<TextView>(R.id.contentTxt)
            val time = itemView.findViewById<TextView>(R.id.time)
            val deviceToken = itemView.findViewById<TextView>(R.id.deviceToken)


                
                contentTxt.text=item.content
                time.text=item.time
                deviceToken.text =item.deviceToken


//            수정 버튼 클릭 이벤트




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