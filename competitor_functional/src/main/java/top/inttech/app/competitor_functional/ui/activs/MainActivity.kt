package top.inttech.app.competitor_functional.ui.activs

import android.content.Intent
import android.os.Bundle
import top.inttech.app.employment_service.model.AppUserInfo
import top.inttech.app.employment_service.ui.activs.PayableActivity


class MainActivity : PayableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fs = Integer.parseInt(AppUserInfo.getEnter(this))
        if (fs == 1) {
            val i = Intent(applicationContext, IntroActivity::class.java)
            startActivity(i)
        }
    }
}
