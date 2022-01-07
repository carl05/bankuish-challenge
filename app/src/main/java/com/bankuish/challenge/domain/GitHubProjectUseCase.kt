package com.bankuish.challenge.domain

import com.bankuish.challenge.data.IGitHubProjectRepository
import retrofit2.Response

class GitHubProjectUseCase(val repository: IGitHubProjectRepository) {
    suspend fun getKotlinProjects(): Response<List<GitHubProject>> {
        val response = repository.getKotlinRepositories()
        return if(response.isSuccessful){
            Response.success(response.body()?.map())
        }else{
            Response.error(response.code(), response.errorBody())
        }
    }
}