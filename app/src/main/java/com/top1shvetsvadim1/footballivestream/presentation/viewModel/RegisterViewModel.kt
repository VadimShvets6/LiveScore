package com.top1shvetsvadim1.footballivestream.presentation.viewModel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.CreateNewAccountCallback
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.LogInCallback
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.ResetPasswordCallback
import com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn.UserRepositoryImpl
import com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn.CreateNewAccountUseCase
import com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn.LogInUseCase
import com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn.RegistrationCheckUseCase
import com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn.ResetPasswordUseCase
import com.top1shvetsvadim1.footballivestream.presentation.*
import kotlinx.coroutines.launch
import android.util.Patterns
import java.util.regex.Pattern


class RegisterViewModel : ViewModel() {


    private val repository = UserRepositoryImpl
    private val registrationCheckUseCase = RegistrationCheckUseCase(repository)
    private val createNewAccountUseCase = CreateNewAccountUseCase(repository)
    private val resetPasswordUseCase = ResetPasswordUseCase(repository)
    private val logInUseCase = LogInUseCase(repository)

    private val _userHasEntered = MutableLiveData<Boolean>()
    val userHasEntered: LiveData<Boolean>
        get() = _userHasEntered

    private val _errorInputPassword = MutableLiveData<Boolean>()
    val errorInputPassword: LiveData<Boolean>
        get() = _errorInputPassword

    private val _errorInputEmail = MutableLiveData<Boolean>()
    val errorInputEmail: LiveData<Boolean>
        get() = _errorInputEmail

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun registerCheck() {
        viewModelScope.launch {
            _userHasEntered.value = registrationCheckUseCase()
        }
    }

    fun createNewAccount(
        activity: FragmentActivity,
        name: String,
        email: String,
        password: String
    ) {
        _state.value = Process(true)
        viewModelScope.launch {
            createNewAccountUseCase(
                activity,
                name,
                email,
                password,
                object : CreateNewAccountCallback {
                    override fun onCallback(value: Boolean) {
                        _state.value = Register(value)
                        _state.value = Process(false)
                    }
                })
        }
    }

    fun logIn(
        activity: FragmentActivity,
        email: String,
        password: String
    ) {
        _state.value = Process(true)
        viewModelScope.launch {
            logInUseCase(
                activity,
                email,
                password,
                object : LogInCallback {
                    override fun onCallback(value: Boolean) {
                        _state.value = LogIn(value)
                        _state.value = Process(false)
                    }
                }
            )
        }
    }

    fun resetPassword(
        activity: FragmentActivity,
        email: String
    ) {
        viewModelScope.launch {
            resetPasswordUseCase(
                activity,
                email,
                object : ResetPasswordCallback {
                    override fun onCallback(value: Boolean) {
                        _state.value = ResetPassword(value)
                    }
                }
            )
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return !pattern.matcher(email).matches()
    }

    fun validateInputRegister(name : String, email : String, password: String) : Boolean{
        var result = true
        if(name.isBlank()){
            _errorInputName.value = true
            result = false
        }
        if(email.isEmpty() || isValidEmail(email)){
            _errorInputEmail.value = true
            result = false
        }
        if(password.isBlank() || password.length < 6){
            _errorInputPassword.value = true
            result = false
        }
        return result
    }

    fun validateInputLogin(email : String, password: String) : Boolean{
        var result = true
        if(email.isEmpty() || isValidEmail(email)){
            _errorInputEmail.value = true
            result = false
        }
        if(password.isBlank() || password.length < 6){
            _errorInputPassword.value = true
            result = false
        }
        return result
    }
}