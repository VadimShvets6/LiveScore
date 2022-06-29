package com.top1shvetsvadim1.footballivestream.data.SingUpAndLogIn

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn.User
import com.top1shvetsvadim1.footballivestream.domain.RegisterAndLogIn.UserRepository

object UserRepositoryImpl : UserRepository {

    private lateinit var auth: FirebaseAuth

    override suspend fun registrationCheck(): Boolean {
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("CheckReg", true.toString())
            return true
        } else {
            Log.d("CheckReg", false.toString())
            return false
        }
    }

    override suspend fun createNewAccount(
        activity: FragmentActivity,
        name: String,
        email: String,
        password: String,
        createNewAccountCallback: CreateNewAccountCallback
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = User(
                        name,
                        email
                    )
                    FirebaseAuth.getInstance().currentUser?.uid?.let {
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(it)
                            .setValue(user).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    createNewAccountCallback.onCallback(true)
                                } else {
                                    createNewAccountCallback.onCallback(false)
                                }
                            }
                    }
                } else {
                    Log.d("CreateAcc", "error")
                    createNewAccountCallback.onCallback(false)
                }
            }
    }

    override suspend fun logIn(
        activity: FragmentActivity,
        email: String,
        password: String,
        logInCallback: LogInCallback
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    logInCallback.onCallback(true)
                } else {
                    logInCallback.onCallback(false)
                }
            }
    }

    override suspend fun resetPassword(
        activity: FragmentActivity,
        email: String,
        resetPasswordCallback: ResetPasswordCallback
    ) {
        auth.useAppLanguage()
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    resetPasswordCallback.onCallback(true)
                } else {
                    resetPasswordCallback.onCallback(false)
                }
            }
    }
}