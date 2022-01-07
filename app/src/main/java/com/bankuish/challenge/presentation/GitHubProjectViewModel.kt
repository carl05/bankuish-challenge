package com.bankuish.challenge.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bankuish.challenge.domain.GitHubProject
import com.bankuish.challenge.domain.GitHubProjectUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

 class GitHubProjectViewModel(private val projectUseCase: GitHubProjectUseCase) : ViewModel(){

    val loading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }
    val projectList = MutableLiveData<List<GitHubProject>>()

     fun getKotlinRepos() {
         viewModelScope.launch {
            val response = projectUseCase.getKotlinProjects()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    projectList.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }

    }
}