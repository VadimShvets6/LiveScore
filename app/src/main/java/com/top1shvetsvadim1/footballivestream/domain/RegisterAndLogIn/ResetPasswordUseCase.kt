package com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn

import androidx.fragment.app.FragmentActivity
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.ResetPasswordCallback

class ResetPasswordUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        activity: FragmentActivity,
        email: String,
        resetPasswordCallback: ResetPasswordCallback
    ) = repository.resetPassword(activity, email, resetPasswordCallback)
}