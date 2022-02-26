package com.checkmooney.naeats.util

import android.util.Log
import com.checkmooney.naeats.data.entities.BaseResponse
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import retrofit2.*


suspend inline fun <Res: BaseResponse> Call<Res>.getResponse() = suspendCoroutine<Res?> {
    enqueue(object : Callback<Res> {
        override fun onResponse(call: Call<Res>, response: Response<Res>) {
            val res = if (response.isSuccessful) {
                 response.body()?.apply {
                    statusCode = response.code()
                    message = response.message()
                }
            } else {
                val jsonObject = JSONObject(response.errorBody()!!.string())
                response.body()?.apply {
                    message = jsonObject.optString("message")
                    statusCode = jsonObject.optInt("statusCode")
                    errorCode = jsonObject.optInt("errorCode")
                }
            }

            Log.d("ApiService", res.toString())
            it.resume(res)
        }

        override fun onFailure(call: Call<Res>, t: Throwable) {
            it.resumeWithException(t)
        }
    })
}

suspend fun <Res : BaseResponse> Call<Unit>.getResponse(baseResponse: Res) = suspendCoroutine<Res> {
    enqueue(object : Callback<Unit> {
        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            val res = baseResponse.apply {
                statusCode = response.code()
                message = response.message()
            }
            Log.d("ApiService", res.toString())
            it.resume(res)
        }

        override fun onFailure(call: Call<Unit>, t: Throwable) {
            it.resumeWithException(t)
        }
    })
}
