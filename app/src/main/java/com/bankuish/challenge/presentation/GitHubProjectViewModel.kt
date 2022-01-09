package com.bankuish.challenge.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankuish.challenge.domain.GitHubProject
import com.bankuish.challenge.domain.GitHubProjectUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GitHubProjectViewModel(private val gitHubProjectUseCase: GitHubProjectUseCase) : ViewModel() {
    private val delayTimeMillis: Long = 1000
    val gitHubLiveData = MutableLiveData<GithubProjectUIState>()

    fun getKotlinRepos(isRefreshing: Boolean) {
        gitHubLiveData.postValue(GithubProjectUIState.Loading)
        viewModelScope.launch {
            val githubProjectUIState = gitHubProjectUseCase.getKotlinProjects(isRefreshing)
            delay(delayTimeMillis)
            gitHubLiveData.postValue(githubProjectUIState)
        }

    }

    sealed class GithubProjectUIState {
        object Loading : GithubProjectUIState()
        object Error : GithubProjectUIState()
        data class Success(val projectList: List<GitHubProject>? = emptyList()) :
            GithubProjectUIState()
    }
}