package com.bankuish.challenge.data

import retrofit2.http.Query

class GitHubProjectProjectRepository(private val retrofitService: GitHubService) :
    IGitHubProjectRepository {
    override suspend fun getKotlinRepositories(
        language: String,
        resultsPerPage: String,
        pageNumber: String
    ) = retrofitService.getGithubProjects(language, resultsPerPage, pageNumber)
}