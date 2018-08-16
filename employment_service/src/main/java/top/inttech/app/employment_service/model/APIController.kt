package top.inttech.app.employment_service.model

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object APIController {

    private val LOG_TAG = "APIController"

    fun getAPI(): APIInterface {
        val gson: Gson = GsonBuilder()
                .setLenient()
                .create()
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (top.inttech.app.employment_service.BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor { message: String -> Logger.writeMsg(LOG_TAG, message) }
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        return Retrofit.Builder()
                .baseUrl(top.inttech.app.employment_service.BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(builder.build())
                .build()
                .create(APIInterface::class.java)

    }
}