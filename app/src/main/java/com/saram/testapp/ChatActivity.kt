package com.saram.testapp

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.saram.testapp.adapter.ChatAdapter
import com.saram.testapp.data.ChatData
import com.saram.testapp.databinding.ActivityChatBinding
import java.text.SimpleDateFormat
import java.util.*


class ChatActivity : BaseActivity() {

    lateinit var binding: ActivityChatBinding

    //   lateinit var mTopicData: UserData
    lateinit var mReplyAdapter: ChatAdapter
    val mReplyList = ArrayList<ChatData>()

    lateinit var mChatData: ChatData

    val realtime =
        FirebaseDatabase.getInstance("https://realtimedb-441a2-default-rtdb.asia-southeast1.firebasedatabase.app/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {


        binding.addReplyBtn1234.setOnClickListener {
            val contentTxt = binding.addReplyEdt1234.text.toString()
            val sdf = SimpleDateFormat("a h:mm")
            val now = Calendar.getInstance()
            val nowStr = sdf.format(now.time)
            val inputMap = HashMap<String, String>()

            inputMap["content"] = contentTxt
            inputMap["time"] = nowStr

            realtime.getReference("data").child("message").child(mChatData.id).child("replies").push().setValue(inputMap)
        }
    }


    override fun setValues() {
        mReplyAdapter = ChatAdapter(mContext, mReplyList)
        binding.profile1234.adapter = mReplyAdapter
        binding.profile1234.layoutManager = LinearLayoutManager(mContext)

        mChatData = intent.getSerializableExtra("content") as ChatData

        binding.contentTxt1.text = mChatData.content
        binding.time1.text = mChatData.time

        realtime.getReference("data").child("message").child(mChatData.id).child("replies").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mReplyList.clear()
                for (child in snapshot.children) {
                    mReplyList.add(
                        ChatData(
                            child.key.toString(),
                            child.child("content").value.toString(),
                            child.child("time").value.toString(),
                            child.child("deviceToken").value.toString()
                        )
                    )
                }
                mReplyAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}