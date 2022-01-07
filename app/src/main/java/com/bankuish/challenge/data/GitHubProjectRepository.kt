package com.bankuish.challenge.data

import kotlinx.coroutines.runBlocking
import retrofit2.Response

 class GitHubProjectRepository(private val retrofitService: GitHubService) :
    IGitHubProjectRepository {
    override fun getKotlinRepositories(
        language: String,
        resultsPerPage: String,
        pageNumber: String
    ): Response<GitHubProjectResponse> {
        return runBlocking {
            retrofitService.getGithubProjects(language, resultsPerPage, pageNumber)
        }
    }
}