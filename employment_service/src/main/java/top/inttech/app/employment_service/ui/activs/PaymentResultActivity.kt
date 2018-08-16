package top.inttech.app.employment_service.ui.activs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_payment_result.*
import ru.tinkoff.acquiring.sdk.Money
import top.inttech.app.employment_service.R

class PaymentResultActivity : AppCompatActivity() {

    private val EXTRA_PRICE = "price"

    fun start(context: Context, price: Money) {
        val intent = Intent(context, PaymentResultActivity::class.java)
        intent.putExtra(EXTRA_PRICE, price)
        context.startActivity(intent)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_result)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val price = intent.getSerializableExtra(EXTRA_PRICE) as Money
                ?: throw IllegalArgumentException("Use start() method to start AboutActivity")

        val coloredPrice = SpannableString(price.toString())
        coloredPrice.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                0,
                coloredPrice.length,
                SpannedString.SPAN_INCLUSIVE_INCLUSIVE
        )

        val text = getString(R.string.payment_result_success, coloredPrice)
        (tv_confirm as TextView).text = text
        (apply as ImageView).setOnClickListener { finish() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
