package com.bankuish.challenge.data

class GitHubProjectProjectRepository(private val retrofitService: GitHubService) :
    IGitHubProjectRepository {
    override suspend fun getKotlinRepositories(
        language: String,
        resultsPerPage: String,
        pageNumber: String
    ) = retrofitService.getGithubProjects(language, resultsPerPage, pageNumber)
}