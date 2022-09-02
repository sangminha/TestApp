package com.saram.testapp.data

import java.io.Serializable

data class ChatData (
    var content : String,
    var time : String,
    var deviceToken : String
): Serializable