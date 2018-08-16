package top.inttech.app.competitor_functional

import android.app.Application
import com.arellomobile.mvp.RegisterMoxyReflectorPackages
import ru.terrakok.cicerone.Cicerone
import top.inttech.app.employment_service.model.ServiceConstants

@RegisterMoxyReflectorPackages("top.inttech.app.employment_service")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceConstants.MAUN_CICERONE = Cicerone.create()

    }
}