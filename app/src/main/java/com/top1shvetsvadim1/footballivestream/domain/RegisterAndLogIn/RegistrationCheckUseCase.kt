package com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn

class RegistrationCheckUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.registrationCheck()
}