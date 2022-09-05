package com.saram.testapp

import android.app.Activity
import android.content.Intent
import com.saram.testapp.adapter.ChatRecyclerAdapter
import com.saram.testapp.data.ChatData
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.saram.testapp.databinding.FragmentChatBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChatFragment : BaseFragment() {

    lateinit var binding: FragmentChatBinding
    val REQ_FOR_REPLY = 104
    lateinit var mReplyAdapter: ChatRecyclerAdapter

    val mReplyList = ArrayList<ChatData>()

    val realtime =
        FirebaseDatabase.getInstance("https://realtimedb-441a2-default-rtdb.asia-southeast1.firebasedatabase.app/")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.addReplyBtn.setOnClickListener {
            val content = binding.contentEdt.text.toString()
            val sdf = SimpleDateFormat("a h:mm")
            val now = Calendar.getInstance()
            val nowStr = sdf.format(now.time)
            val deviceToken = "123321"
            val inputMap = HashMap<String, String>()
            inputMap["content"] = content
            inputMap["deviceToken"] = deviceToken
            inputMap["time"] = nowStr


            realtime.getReference("data")
                .child("message")
                .push()
                .setValue(inputMap)

            binding.contentEdt.setText("")

        }
        realtime.getReference("data").child("message").addValueEventListener(
            object : ValueEventListener {
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

            }
        )
    }


    override fun setValues() {
        mReplyAdapter = ChatRecyclerAdapter(mContext, mReplyList)
        binding.rvProfile.adapter = mReplyAdapter
        binding.rvProfile.layoutManager = LinearLayoutManager(mContext)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == REQ_FOR_REPLY) {
//                val chatData = data?.getSerializableExtra("chatData") as ChatData
//                val position = data.getIntExtra("position", 0)
//                 mReplyList[position] = chatData
//                mReplyAdapter.notifyItemChanged(position)
//            }
//        }
//    }
}