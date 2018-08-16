package top.inttech.app.employment_service.ui.activs

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.BottomNavigationView
import android.support.multidex.MultiDex
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import ru.tinkoff.acquiring.sdk.Money
import ru.tinkoff.acquiring.sdk.OnPaymentListener
import ru.tinkoff.acquiring.sdk.PayFormActivity
import top.inttech.app.employment_service.R
import top.inttech.app.employment_service.model.AppUserInfo
import top.inttech.app.employment_service.model.ResponceConnect
import top.inttech.app.employment_service.presentation.presenter.activs.PayablePresenter
import top.inttech.app.employment_service.presentation.view.activs.PayableView
import java.lang.Exception

open class PayableActivity : MvpAppCompatActivity(), PayableView, OnPaymentListener {

    val LOG_TAG = "PayableActivity"
    private val REQUEST_CODE_PAY = 1

    private var paymentAmount: Money? = null
    private var paymentDescription: String? = null
    private var paymentTitle: String? = null
    private var sharedPreferences: SharedPreferences? = null

    private val mOnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        mainPresenter.onNavigationItemSelected(
                AppUserInfo.getPassword(baseContext),
                AppUserInfo.isLogged(baseContext),
                item)
    }

    @InjectPresenter
    lateinit var mainPresenter: PayablePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        setContentView(R.layout.activity_main)
        MultiDex.install(this)

        if (mainPresenter.hasConnect()) {
            mainPresenter.signUp()
        }

        bnve.onNavigationItemSelectedListener = mOnNavigationItemSelectedListener
        bnve.enableShiftingMode(false)
        bnve.enableItemShiftingMode(false)

//        setRatingApp()
//        mMainPresenter.setGlobalParams(packageName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PAY) {
            PayFormActivity.dispatchResult(resultCode, data, this)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("payment_amount", paymentAmount)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        paymentAmount = if (savedInstanceState == null) null else savedInstanceState.getSerializable("payment_amount") as Money
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSuccess(paymentId: Long) {
        PaymentResultActivity().start(this, paymentAmount!!)
    }

    override fun onCancelled() {
        Toast.makeText(this, R.string.payment_cancelled, Toast.LENGTH_SHORT).show()
    }

    override fun onError(e: Exception) {
        Toast.makeText(this, R.string.payment_failed, Toast.LENGTH_SHORT).show()
        Log.e(LOG_TAG, e.message, e)
    }

    private fun isCustomKeyboardEnabled(): Boolean {
        val key = getString(R.string.acq_sp_use_system_keyboard)
        return !sharedPreferences!!.getBoolean(key, false)
    }

    private fun getTerminalId(): String? {
        val key = getString(R.string.acq_sp_terminal_id)
        val fallback = getString(R.string.acq_sp_default_value_terminal_id)
        return sharedPreferences!!.getString(key, fallback)
    }

    override fun showServersStatusToastOK(body: ResponceConnect){
        val msg = getString(R.string.msg_servers_status) + body.msg
        Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
    }

    override fun showServersStatusToastBad(servesStatus: Boolean) {
        var msg = getString(R.string.msg_servers_status)
        if (servesStatus)
            msg += getString(R.string.msg_servers_invalid)
        else
            msg += getString(R.string.msg_servers_bad)
        Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
    }
}
