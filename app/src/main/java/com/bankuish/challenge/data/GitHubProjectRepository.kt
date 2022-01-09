package com.bankuish.challenge.data

import kotlinx.coroutines.runBlocking
import retrofit2.Response

 class GitHubProjectRepository(private val retrofitService: GitHubService) :
    IGitHubProjectRepository {
    override suspend fun getKotlinRepositories(
        language: String,
        resultsPerPage: String,
        pageNumber: String
    ): Response<GitHubProjectResponse> {
        return retrofitService.getGithubProjects(language, resultsPerPage, pageNumber)

    }
}