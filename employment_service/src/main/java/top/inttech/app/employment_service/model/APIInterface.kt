package top.inttech.app.employment_service.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {

    @GET("connect/")
    fun getConnect(@Query("deviceid") deviceid : Int): Call<ResponceConnect>

}
