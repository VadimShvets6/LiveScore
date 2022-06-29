package com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn

import androidx.fragment.app.FragmentActivity
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.CreateNewAccountCallback
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.LogInCallback
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.ResetPasswordCallback

interface UserRepository {

    suspend fun registrationCheck() : Boolean
    suspend fun createNewAccount(
        activity: FragmentActivity,
        name: String,
        email: String,
        password: String,
        createNewAccountCallback: CreateNewAccountCallback
    )

    suspend fun resetPassword(
        activity: FragmentActivity,
        email: String,
        resetPasswordCallback: ResetPasswordCallback
    )

    suspend fun logIn(
        activity: FragmentActivity,
        email: String,
        password: String,
        logInCallback: LogInCallback
    )
}