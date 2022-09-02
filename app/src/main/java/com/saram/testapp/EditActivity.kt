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
    val id = 0
    val id2 = 1

    //    몇번째 리스트에 있던 댓글인지를 받아올 position 값
    var position: Int = 0
    lateinit var mChatData: ChatData

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("테스트-1", id.toString())
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
//        RecyclerView에서 수정을 하기 위해 intent를 사용한 경우 position 값이 intent안에 잇음
//        Int의 경우 intent로 값을 가져올 경우 defaultValue라는 기본 값을 지정해야함.
        position = intent.getIntExtra("position", 0)
//        우리가 수정할 한칸의 chatData
        mChatData = intent.getSerializableExtra("chatData") as ChatData
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }


    override fun setValues() {

    }
}