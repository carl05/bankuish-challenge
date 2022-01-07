package com.bankuish.challenge.data

import retrofit2.Response

interface IGitHubProjectRepository{

    suspend fun getKotlinRepositories(): Response<GitHubProjectResponse>
}