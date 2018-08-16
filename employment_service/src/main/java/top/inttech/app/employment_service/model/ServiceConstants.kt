package top.inttech.app.employment_service.model

import android.os.Bundle
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import top.inttech.app.employment_service.BuildConfig.FRAG5_MORE_ACCOUNT_EMAIL
import top.inttech.app.employment_service.BuildConfig.FRAG5_MORE_ACCOUNT_PIN

object ServiceConstants {

    var MAUN_CICERONE: Cicerone<Router>? = null

    public var isLoginSuccessful: Boolean = false

    public var nextFragAfterLogin: String? = null

    fun checkLoginSuccessfulAndStartFrag(password: String) {
        if (!isLoginSuccessful) {
            when (password) {
                "" ->
                    //Открыть окно ввода email
                    MAUN_CICERONE!!.getRouter().navigateTo(FRAG5_MORE_ACCOUNT_EMAIL)
                else -> {
                    //Открыть диалог ввода действующего пароля
                    val data = Bundle()
//                    data.putInt("action", Frag5MoreAccountPin.TYPE_ACCEPT)
                    MAUN_CICERONE!!.getRouter().navigateTo(FRAG5_MORE_ACCOUNT_PIN, data)
                }
            }
        } else {
            //Открыть переданный фрагмент
            MAUN_CICERONE!!.getRouter().navigateTo(nextFragAfterLogin)
        }
    }
}