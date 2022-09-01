package com.saram.testapp

import android.content.Intent
import com.saram.testapp.adapter.ChatRecyclerAdapter
import com.saram.testapp.data.ChatData
import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
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
        val realtime =
            FirebaseDatabase.getInstance(" https://testapp-80b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        binding.addReplyBtn.setOnClickListener {
            val content = binding.contentEdt.text.toString()
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

            binding.contentEdt.setText("")

        }
        realtime.getReference("data").child("meassge").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatData = ChatData(
                        snapshot.child("content").value.toString(),
                        snapshot.child("time").value.toString(),
                        snapshot.child("deviceToken").value.toString()
                    )
                    mReplyList.add(0, chatData)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == REQ_FOR_REPLY) {
                val reply = data?.getStringExtra("string")!!
                val reply1 = data?.getStringExtra("string")!!
                val reply2 = data?.getStringExtra("string")!!
                val position = data?.getIntExtra("position", 0)!!
                 mReplyList.add(position,ChatData(reply,reply1,reply2))
                mReplyAdapter.notifyItemChanged(REQ_FOR_REPLY)
            }
        }
    }
}