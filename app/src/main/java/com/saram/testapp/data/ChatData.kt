package com.saram.testapp.data

import java.io.Serializable

data class ChatData (
    val content : String,
    val time : String,
    val deviceToken : String
): Serializable