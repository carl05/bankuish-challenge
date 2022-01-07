package com.bankuish.challenge.domain

import com.bankuish.challenge.data.IGitHubProjectRepository
import retrofit2.Response

class GitHubProjectUseCase(val repository: IGitHubProjectRepository) {
    private val LANGUAGE = "kotlin"
    private val RESULTS_PER_PAGE = "40"
    private val PAGE_NUMBER = "1"
    suspend fun getKotlinProjects(): Response<List<GitHubProject>> {
        val response = repository.getKotlinRepositories(LANGUAGE, RESULTS_PER_PAGE, PAGE_NUMBER)
        return if(response.isSuccessful){
            Response.success(response.body()?.map())
        }else{
            Response.error(response.code(), response.errorBody())
        }
    }
}