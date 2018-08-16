package top.inttech.app.employment_service.presentation.presenter.activs

import android.view.MenuItem
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import top.inttech.app.employment_service.BuildConfig.*
import top.inttech.app.employment_service.R
import top.inttech.app.employment_service.model.AppCoroutines
import top.inttech.app.employment_service.model.ServiceConstants
import top.inttech.app.employment_service.presentation.view.activs.PayableView

@InjectViewState
class PayablePresenter : MvpPresenter<PayableView>() {

    private val LOG_TAG = "PayablePresenter"

    fun hasConnect(): Boolean {
        return AppCoroutines.hasServersConnect(viewState)
    }

    fun signUp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onNavigationItemSelected(password: String, logged: Boolean, item: MenuItem) : Boolean {
        when (item.itemId) {
            R.id.searchAction -> ServiceConstants.nextFragAfterLogin = FRAG1_SEARCH
            R.id.favorsAction -> ServiceConstants.nextFragAfterLogin = FRAG2_FAVOR
            R.id.correspAction -> ServiceConstants.nextFragAfterLogin = FRAG3_CORRESP
            R.id.balanceAction -> ServiceConstants.nextFragAfterLogin = FRAG4_BALANCE
            R.id.moreAction -> ServiceConstants.nextFragAfterLogin = FRAG5_MORE
        }
        if (logged) {
            ServiceConstants.checkLoginSuccessfulAndStartFrag(password)
            return true
        } else return false
    }
}
