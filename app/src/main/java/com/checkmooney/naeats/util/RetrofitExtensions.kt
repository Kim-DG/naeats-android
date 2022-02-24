package com.checkmooney.naeats.util

import com.checkmooney.naeats.data.entities.AuthTokenResponse
import com.checkmooney.naeats.data.entities.BaseResponse
import com.checkmooney.naeats.data.entities.RefreshAccessTokenResponse
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend inline fun <Res : BaseResponse> Call<Res>.callback() = suspendCoroutine<Response<Res>?> {
    enqueue(object : Callback<Res> {
        override fun onResponse(call: Call<Res>, response: Response<Res>) {
            it.resume(response)
        }

        override fun onFailure(call: Call<Res>, t: Throwable) {
            it.resumeWithException(t)
        }
    })
}

fun <Res : BaseResponse> Response<Res>.setResponse(res: Res): Res? {
    return if (isSuccessful) body()?.apply {
        statusCode = code()
        message = message()
    } else {
        val jsonObject = JSONObject(errorBody()!!.string())
        res.apply {
            message = jsonObject.optString("message")
            statusCode = jsonObject.optInt("statusCode")
            errorCode = jsonObject.optInt("errorCode")
        }
    }
}
