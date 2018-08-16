package top.inttech.app.employment_service.model

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class APIControllerInstrumentedTest {

    private val LOG_TAG = "APIControllerInstrumentedTest"

    @Test
    fun connect_isCorrect() {
        var responce : retrofit2.Response<ResponceConnect>? = null
        try {
            responce = APIController.getAPI().getConnect(209).execute()
        } catch (e : Throwable) {
            Logger.writeMsg(LOG_TAG, e.message!!)
        }
        assertNotNull(responce)
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("top.inttech.app.employment_service.test", appContext.packageName)
    }
}
