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
    private val delayTimeMillis: Long = 3000
    val gitHubLiveDate = MutableLiveData<GithubProjectUIState>()

    fun getKotlinRepos() {
        gitHubLiveDate.postValue(GithubProjectUIState.Loading)
        viewModelScope.launch {
            val response = projectUseCase.getKotlinProjects()
            withContext(Dispatchers.Main) {
                delay(delayTimeMillis)
                if (response.isSuccessful) {
                    gitHubLiveDate.postValue(GithubProjectUIState.Success(response.body()))
                } else {
                    gitHubLiveDate.postValue(GithubProjectUIState.Error)
                }
            }
        }

    }

    sealed class GithubProjectUIState {
        object Loading : GithubProjectUIState()
        object Error : GithubProjectUIState()
        data class Success(val projectList: List<GitHubProject> ?= emptyList()) : GithubProjectUIState()
    }
}