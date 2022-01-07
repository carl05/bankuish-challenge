package com.bankuish.challenge.data

class GitHubProjectRepository (private val retrofitService: GitHubService) : IGitHubRepository {

    override suspend fun getKotlinRepositories() = retrofitService.getGithubProjects()
}