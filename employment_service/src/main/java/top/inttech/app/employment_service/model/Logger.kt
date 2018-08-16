package top.inttech.app.employment_service.model

import android.util.Log

class Logger {
    companion object {
        fun writeMsg(tag : String, msg : String){
            Log.i(tag, ">>------------------------------------------------------------------------------------------------------->>")
            Log.i(tag, msg)
        }
    }
}