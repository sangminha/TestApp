package com.saram.testapp
import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.saram.testapp.adapter.ChatAdapter

import com.saram.testapp.adapter.ChatRecyclerAdapter
import com.saram.testapp.data.ChatData
import com.saram.testapp.databinding.ActivityChatBinding

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChatActivity : BaseActivity() {
    var i = 1
    lateinit var binding: ActivityChatBinding

    //   lateinit var mTopicData: UserData
    lateinit var mReplyAdapter: ChatAdapter
    val mReplyList = ArrayList<ChatData>()

    var reply = ""

    //    댓글을 쓰기 위한 RequestCode
    val REQ_FOR_REPLY = 1004

    //    댓글 수정 확인을 위한 RequestCode
    val REQ_FOR_EDIT = 1005
    val REQ = 1006
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
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
      binding.addReplyBtn1234.setOnClickListener {
          val contentTxt = binding.addReplyEdt1234.text.toString()
          val alpa = mReplyList.size
          val reply = intent.getSerializableExtra("content") as ChatData
          mReplyList.add(alpa, ChatData(contentTxt,reply.time,reply.deviceToken))

          mReplyAdapter.notifyDataSetChanged()
      }
    }


    override fun setValues() {
        mReplyAdapter = ChatAdapter(mContext, mReplyList)
        binding.profile1234.adapter = mReplyAdapter
        binding.profile1234.layoutManager = LinearLayoutManager(mContext)


       val reply = intent.getSerializableExtra("content") as ChatData

        binding.contentTxt1.text = reply.content
        binding.time1.text =reply.time
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ) {
                val chatData = data?.getSerializableExtra("chatData") as ChatData
                val position = data.getIntExtra("position", 0)
                mReplyList[position] = chatData
                mReplyAdapter.notifyItemChanged(position)
            }
//            RecyclerView에서 수정버튼 클릭해서 돌아온 경우 해당 리스트 수정 이벤트 처리
        } else if (requestCode == REQ_FOR_EDIT) {
            val chatData = data?.getSerializableExtra("chatData") as ChatData
            val position = data.getIntExtra("position", 0)
            val alpa = position.plus(i)
            mReplyList[alpa] = chatData
//                수정된 리스트를 리싸이클러뷰에 반영
            mReplyAdapter.notifyDataSetChanged()
        }
    }


}