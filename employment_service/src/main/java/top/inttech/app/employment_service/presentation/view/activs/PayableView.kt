package top.inttech.app.employment_service.presentation.view.activs

import com.arellomobile.mvp.MvpView
import top.inttech.app.employment_service.model.ResponceConnect

interface PayableView : MvpView {
    fun showServersStatusToastBad(servesStatus: Boolean)
    fun showServersStatusToastOK(body: ResponceConnect)

}
