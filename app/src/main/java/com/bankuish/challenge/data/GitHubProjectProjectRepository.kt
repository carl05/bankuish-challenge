package com.bankuish.challenge.data

class GitHubProjectProjectRepository (private val retrofitService: GitHubService) : IGitHubProjectRepository {

    override suspend fun getKotlinRepositories() = retrofitService.getGithubProjects()
}