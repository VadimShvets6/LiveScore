package com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn

import androidx.fragment.app.FragmentActivity
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.CreateNewAccountCallback

class CreateNewAccountUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        activity: FragmentActivity,
        name: String,
        email: String,
        password: String,
        createNewAccountCallback: CreateNewAccountCallback
    ) = repository.createNewAccount(activity,name,email,password, createNewAccountCallback)
}