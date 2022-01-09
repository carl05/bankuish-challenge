package com.bankuish.challenge.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankuish.challenge.domain.GitHubProject
import com.bankuish.challenge.domain.GitHubProjectUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GitHubProjectViewModel(private val projectUseCase: GitHubProjectUseCase) : ViewModel() {
    private val delayTimeMillis: Long = 1000
    val gitHubLiveData = MutableLiveData<GithubProjectUIState>()

    fun getKotlinRepos(isRefreshing: Boolean) {
        gitHubLiveData.postValue(GithubProjectUIState.Loading)
        viewModelScope.launch {
            val response = projectUseCase.getKotlinProjects(isRefreshing)
            delay(delayTimeMillis)
            if (response.isSuccessful) {
                gitHubLiveData.postValue(GithubProjectUIState.Success(response.body()))
            } else {
                gitHubLiveData.postValue(GithubProjectUIState.Error)
            }
        }

    }

    sealed class GithubProjectUIState {
        object Loading : GithubProjectUIState()
        object Error : GithubProjectUIState()
        data class Success(val projectList: List<GitHubProject>? = emptyList()) :
            GithubProjectUIState()
    }
}