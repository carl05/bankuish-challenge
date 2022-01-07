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

//    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
//        onError("Exception handled: ${throwable.localizedMessage}")
//    }
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

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

}