package com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn

import androidx.fragment.app.FragmentActivity
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.LogInCallback

class LogInUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        activity: FragmentActivity,
        email: String,
        password: String,
        logInCallback: LogInCallback
    ) = repository.logIn(activity,email, password,logInCallback)
}