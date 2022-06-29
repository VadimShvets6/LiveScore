package com.top1shvetsvadim1.footballivestream.presentation

sealed class State

class Register(val isSuccess : Boolean = false) : State()
class LogIn(val isSuccess : Boolean = false) : State()
class Process(val isLoading : Boolean = false) : State()
class ResetPassword(val isReset : Boolean = false) : State()