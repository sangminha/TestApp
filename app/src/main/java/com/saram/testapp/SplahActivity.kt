package com.saram.testapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.saram.testapp.databinding.ActivitySplahBinding


class SplahActivity : BaseActivity() {

    lateinit var binding : ActivitySplahBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this,R.layout.activity_splah)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.unnamed.setOnClickListener {
            val myIntent = Intent(mContext, MainActivity::class.java)
            startActivity(myIntent)

            finish()
        }

        }
    override fun setValues() {

    }


}