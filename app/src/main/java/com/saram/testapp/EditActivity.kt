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
    var position : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("테스트-1",id.toString())
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)
//        RecyclerView에서 수정을 하기 위해 intent를 사용한 경우 position 값이 intent안에 잇음
//        Int의 경우 intent로 값을 가져올 경우 defaultValue라는 기본 값을 지정해야함.
        position = intent.getIntExtra("position", 0)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {
        val realtime =
            FirebaseDatabase.getInstance(" https://testapp-80b90-default-rtdb.asia-southeast1.firebasedatabase.app/")
        binding.Btn123.setOnClickListener {
            val inputContent = binding.text12.text.toString()

            val sdf = SimpleDateFormat("a h:mm")
            val now = Calendar.getInstance()
            val nowStr = sdf.format(now.time)
            val deviceToken = "123321"
            val inputMap = HashMap<String, String>()
            inputMap["content"] = inputContent
            inputMap["deviceToekn"] = deviceToken
            inputMap["time"] = nowStr


            realtime.getReference("data")
                .child("meassge")
                .setValue(inputMap)

        }
        realtime.getReference("data").child("meassge").addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatData = ChatData(
                        snapshot.child("content").value.toString(),
                        snapshot.child("time").value.toString(),
                        snapshot.child("deviceToken").value.toString()
                    )
                    val resultIntent = Intent()
//            기록된 값 혹은 수정된 값을 담아주는 putExtra
                    resultIntent.putExtra("string", chatData.toString())

//            만약 수정일 경우 위에서 intent로 받아온 position을 넣어서 Recycler Activity에서 활용
                    resultIntent.putExtra("position", position)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }

                override fun onCancelled(error: DatabaseError) {

                }


                // alert을 띄우고, alert의 값을 받아와서
                // contentTxt의 text 속성을 변경
            })

        }



    override fun setValues() {

    }
}