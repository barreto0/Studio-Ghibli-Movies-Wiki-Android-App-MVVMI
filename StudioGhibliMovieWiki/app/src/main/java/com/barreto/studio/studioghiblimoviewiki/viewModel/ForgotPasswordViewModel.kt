package com.barreto.studio.studioghiblimoviewiki.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.barreto.studio.studioghiblimoviewiki.interactor.ForgotPasswordInteractor

class ForgotPasswordViewModel(val app: Application) : AndroidViewModel(app) {

    val interactor = ForgotPasswordInteractor(app.applicationContext)
    val result = MutableLiveData<Boolean>()

    fun resetPassword(email: String){
        interactor.resetPassword(email){
            result.value = it
        }
    }
}