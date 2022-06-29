package com.top1shvetsvadim1.footballivestream.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.top1shvetsvadim1.footballivestream.data.MatchRepository.MatchRepositoryImpl
import com.top1shvetsvadim1.footballivestream.domain.InfoFootball.DownloadingMatchDataUseCase
import com.top1shvetsvadim1.footballivestream.domain.InfoFootball.MatchInformation
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class InfoViewModel : ViewModel() {

    private val repository = MatchRepositoryImpl

    private val downloadingMatchDataUseCase = DownloadingMatchDataUseCase(repository)

    private val _matchInformation = MutableLiveData<List<MatchInformation>>()
    val matchInformation: LiveData<List<MatchInformation>>
        get() = _matchInformation

    fun downloadingMatchData(){
        val deferred = viewModelScope.async {
            val result = downloadingMatchDataUseCase()
            result
        }
        viewModelScope.launch {
            _matchInformation.value = deferred.await()
        }
    }
}