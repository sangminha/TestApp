package com.saram.testapp


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.saram.testapp.data.ChatData
import com.saram.testapp.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class EditActivity : BaseActivity() {

    lateinit var binding: ActivityEditBinding

    lateinit var mChatData: ChatData

    val realtime =
        FirebaseDatabase.getInstance("https://realtimedb-441a2-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("data").child("message")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
//        우리가 수정할 한칸의 chatData
        mChatData = intent.getSerializableExtra("chatData") as ChatData
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.editBtn.setOnClickListener {
            val inputContent = binding.contentEdt.text.toString()
            val inputMap = HashMap<String, String>()
            inputMap["content"] = inputContent
            inputMap["deviceToken"] = mChatData.deviceToken
            inputMap["time"] = mChatData.time

            realtime.child(mChatData.id).updateChildren(inputMap as Map<String, Any>)
            finish()
        }
    }


    override fun setValues() {
        binding.contentEdt.setText(mChatData.content)
    }
}