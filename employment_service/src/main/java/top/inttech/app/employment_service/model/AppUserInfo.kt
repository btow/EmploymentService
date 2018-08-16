package top.inttech.app.employment_service.model

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import top.inttech.app.employment_service.BuildConfig.*
import top.inttech.app.employment_service.R
import top.inttech.app.employment_service.model.ServiceConstants.MAUN_CICERONE

object AppUserInfo {

    private var UserInfo: SharedPreferences? = null

    fun getEnter(ctx: Context): String {
        var code = getValue(ctx, ENTER)
        if (code == "") code = "0"
        return code
    }

    private fun getValue(ctx: Context, name: String): String {
        UserInfo = ctx.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        return UserInfo!!.getString(name, "")!!
    }

    fun getPassword(ctx: Context): String {
        return getValue(ctx, PASSWORD)
    }

    fun getCheckMail(ctx: Context): String {
        return getValue(ctx, CHECK_MAIL)
    }

    fun isLogged(ctx: Context): Boolean {
        if (getCheckMail(ctx) == "") {

            val builder = AlertDialog.Builder(ctx)
            builder.setTitle(ctx.getString(R.string.attention))
            builder.setMessage(ctx.getString(R.string.notmail_msg))
            builder.setPositiveButton(R.string.ok) { dialog, id -> MAUN_CICERONE!!.getRouter().navigateTo(FRAG5_MORE_ACCOUNT_EMAIL) }
            builder.setNegativeButton(R.string.cancel) { dialog, id -> dialog.cancel() }
            val dialog = builder.create()
            dialog.show()
            return false
        } else {
            return true
        }
    }

}
