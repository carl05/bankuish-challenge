package com.bankuish.challenge.data

import retrofit2.Response

interface IGitHubRepository{

    suspend fun getKotlinRepositories(): Response<GitHubProjectResponse>
}