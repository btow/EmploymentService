package top.inttech.app.employment_service.model

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import top.inttech.app.employment_service.presentation.view.activs.PayableView

object AppCoroutines {

    private val SERVER_NOT_FOUND: Boolean = false
    private val SERVER_INVALID: Boolean = true

    fun hasServersConnect(viewStateProvider: PayableView): Boolean {
        var result = false
        launch(UI) {
            var responce: Response<ResponceConnect>? = null
            try {
                responce = async(CommonPool) {
                    APIController.getAPI().getConnect(209).execute()
                }.await()
            } catch (e: Throwable) {
                e.printStackTrace()
                viewStateProvider.showServersStatusToastBad(SERVER_NOT_FOUND)
                return@launch
            }
            val body = responce.body()
            if (body != null) {
                kotlin.run { viewStateProvider.showServersStatusToastOK(body) }
                result = true
                return@launch
            } else {
                kotlin.run { viewStateProvider.showServersStatusToastBad(SERVER_INVALID) }
                return@launch
            }
        }
        return result
    }
}