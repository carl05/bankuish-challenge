package com.bankuish.challenge.domain

import com.bankuish.challenge.data.IGitHubProjectRepository
import com.bankuish.challenge.presentation.GitHubProjectViewModel
import retrofit2.Response
import kotlin.random.Random

open class GitHubProjectUseCase(private val repository: IGitHubProjectRepository) {
    private val LANGUAGE = "kotlin"
    private var RESULTS_PER_PAGE = "40"
    private var PAGE_NUMBER = "1"
    suspend fun getKotlinProjects(isRefreshing: Boolean? = false): GitHubProjectViewModel.GithubProjectUIState {
        return try {
            if (isRefreshing == true) generateRandomValues()
            val response = repository.getKotlinRepositories(LANGUAGE, RESULTS_PER_PAGE, PAGE_NUMBER)
            if (response.isSuccessful) {
                GitHubProjectViewModel.GithubProjectUIState.Success(response.body()?.map())
            } else {
                GitHubProjectViewModel.GithubProjectUIState.Error
            }
        } catch (e: Exception) {
            GitHubProjectViewModel.GithubProjectUIState.Error
        }
    }

    private fun generateRandomValues() {
        getRandomResultsNumber()
        getRandomPageNumber()
    }

    private fun getRandomPageNumber() {
        PAGE_NUMBER = Random.nextInt(2, 10).toString()
    }

    private fun getRandomResultsNumber() {
        RESULTS_PER_PAGE = Random.nextInt(0, 100).toString()
    }
}