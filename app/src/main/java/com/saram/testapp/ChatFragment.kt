package com.saram.testapp

import Adapter.ChatRecyclerAdapter
import Data.ChatData
import android.content.Intent
import android.os.Bundle
import android.text.Editable

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.saram.testapp.databinding.ActivityChatBinding

import com.saram.testapp.databinding.FragmentChatBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChatFragment : BaseFragment() {

    lateinit var binding: FragmentChatBinding


    //   lateinit var mTopicData: UserData
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

//    override fun onResume() {
//        super.onResume()
//        val reply: String? = intent.getStringExtra("string")
//        if (reply != null) {
//            mReplyList.add(reply)
//            mReplyAdapter.notifyDataSetChanged()
//        }
//    }

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


}