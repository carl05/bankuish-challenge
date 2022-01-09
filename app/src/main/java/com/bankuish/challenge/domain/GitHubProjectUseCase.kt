package com.bankuish.challenge.domain

import com.bankuish.challenge.data.IGitHubProjectRepository
import retrofit2.Response
import kotlin.random.Random

class GitHubProjectUseCase(private val repository: IGitHubProjectRepository) {
    private val LANGUAGE = "kotlin"
    private var RESULTS_PER_PAGE = "40"
    private var PAGE_NUMBER = "1"
    suspend fun getKotlinProjects(isRefreshing: Boolean? = false): Response<List<GitHubProject>> {
        if(isRefreshing == true) generateRandomValues()
        val response = repository.getKotlinRepositories(LANGUAGE, RESULTS_PER_PAGE, PAGE_NUMBER)
        return if (response.isSuccessful) {
            Response.success(response.body()?.map())
        } else {
            Response.error(response.code(), response.errorBody())
        }
    }
    private fun generateRandomValues(){
        getRandomResultsNumber()
        getRandomPageNumber()
    }

    private fun getRandomPageNumber() {
        PAGE_NUMBER = Random.nextInt(2,10).toString()
    }

    private fun getRandomResultsNumber() {
        RESULTS_PER_PAGE = Random.nextInt(0,100).toString()
    }
}