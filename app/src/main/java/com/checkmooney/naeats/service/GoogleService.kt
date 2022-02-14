package com.checkmooney.naeats.service

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GoogleService @Inject constructor(private val context: Context) {
    var googleAuthCallback: GoogleAuthCallback? = null

    // NOTE: 다른 idp 추가에 쉽게 대응하기 위해 확장성을 고려하여 인터페이스화 하면 좋겠지만, 토이 프로젝트라 그냥 구현함
    suspend fun getAuthToken() = suspendCoroutine<String> { cont ->
        googleAuthCallback = object : GoogleAuthCallback {
            override fun onComplete(task: Task<GoogleSignInAccount>) {
                try {
                    val result = task.getResult(ApiException::class.java)
                    cont.resume(result.idToken ?: "")
                } catch (e: ApiException) {
                    cont.resumeWithException(e)
                }
            }
        }
        val intent = Intent(context, GoogleAuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            var easd = e.localizedMessage
        }

    }
}

interface GoogleAuthCallback {
    fun onComplete(task: Task<GoogleSignInAccount>)
}
