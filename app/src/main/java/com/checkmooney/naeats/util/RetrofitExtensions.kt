package com.checkmooney.naeats.util

import com.checkmooney.naeats.data.entities.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend inline fun <Res : BaseResponse> Call<Res>.callback() = suspendCoroutine<Res?> {
    enqueue(object : Callback<Res> {
        override fun onResponse(call: Call<Res>, response: Response<Res>) {
            it.resume(response.body())
        }

        override fun onFailure(call: Call<Res>, t: Throwable) {
            it.resumeWithException(t)
        }
    })
}
